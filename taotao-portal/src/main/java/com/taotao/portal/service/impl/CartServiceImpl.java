package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加商品购物车列表service
 * Created by Administrator on 2017/5/2.
 */
@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private ItemService itemService;

    @Value("${COOKIE_EXPIRE}")
    private Integer COOKIE_EXPIRE;

    @Override
    public TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
       // 1、接收商品id
       // 2、从cookie中取购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        // 3、从商品列表中查询列表是否存在此商品
        boolean haveFlg = false;
        for (CartItem cartItem : cartItemList){
            // 4、如果存在商品的数量加上参数中的商品数量
            if(cartItem.getId().longValue() == itemId){
                cartItem.setNum(cartItem.getNum()+num);
                haveFlg = true;
                break;
            }
        }
       // 5、如果不存在，调用rest服务，根据商品id获得商品数据。
        if(!haveFlg){
            TbItem tbItem = itemService.getTbItemById(itemId);
            //转换成CartItem
            CartItem cartItem = new CartItem();
            cartItem.setId(itemId);
            cartItem.setNum(num);
            cartItem.setPrice(tbItem.getPrice());
            cartItem.setTitle(tbItem.getTitle());
            if(StringUtils.isNotBlank(tbItem.getImage())){
                 String images = tbItem.getImage();
                String[] strings = images.split(",");
                cartItem.setImage(strings[0]);
            }
            // 6、把商品数据添加到列表中
            cartItemList.add(cartItem);
        }
       // 7、把购物车商品列表写入cookie
        CookieUtils.setCookie(request,response,"TT_CART",
                JsonUtils.objectToJson(cartItemList),COOKIE_EXPIRE,true);
       // 8、返回TaotaoResult
        return TaotaoResult.ok();
    }


    //取购物车商品列表
    private List<CartItem> getCartItemList(HttpServletRequest request){
        try {
        //从cookie中取商品列表
        String json = CookieUtils.getCookieValue(request, "TT_CART", true);
        //把json转换成java对象
        List<CartItem> cartItemList = JsonUtils.jsonToList(json, CartItem.class);

        return cartItemList==null?new ArrayList<CartItem>():cartItemList;

        }catch (Exception e){
            return new ArrayList<CartItem>();
        }
    }

    //展示购物车商品列表
    @Override
    public List<CartItem> getCartItems(HttpServletRequest request) {
        // 从cookie中取购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        return cartItemList;
    }

    //修改购物车商品数量
    @Override
    public TaotaoResult updateCartItemNum(long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
        // 从cookie中取购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);

        for (CartItem cartItem : cartItemList){
            //根据商品id查询商品
            if(cartItem.getId() == itemId){
                //更新数量
                cartItem.setNum(num);
                break;
            }
        }
        //写入cookie
        CookieUtils.setCookie(request,response,"TT_CART",
                JsonUtils.objectToJson(cartItemList),COOKIE_EXPIRE,true);
        return TaotaoResult.ok();
    }

    //删除购物车商品
    @Override
    public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
        //1、接收商品id
        //2、从cookie中取购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        //3、找到对应id的商品
        for (CartItem cartItem : cartItemList){
            //根据商品id查询商品
            if(cartItem.getId() == itemId){
                //4、删除商品。
                cartItemList.remove(cartItem);
                break;
            }
        }
        //5、再重新把商品列表写入cookie。
        CookieUtils.setCookie(request,response,"TT_CART",
                JsonUtils.objectToJson(cartItemList),COOKIE_EXPIRE,true);
        //6、返回成功
        return TaotaoResult.ok();
    }

}
