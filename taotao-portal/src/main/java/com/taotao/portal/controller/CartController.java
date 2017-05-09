package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 添加购物车到商品列表Controller
 * Created by Administrator on 2017/5/2.
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    //添加购物车
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId,Integer num,
            HttpServletRequest request, HttpServletResponse response){
       TaotaoResult result = cartService.addCart(itemId, num, request, response);
        return "cartSuccess";
    }

    //展示购物车商品列表
    @RequestMapping("/cart/cart")
    public String getCartItemList(HttpServletRequest request, Model model){
        List<CartItem> cartItemList = cartService.getCartItems(request);
        model.addAttribute("cartList", cartItemList);
        return "cart";
    }

    //修改购物车商品数量
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateCartItemNum(@PathVariable long itemId, @PathVariable Integer num,
                                          HttpServletRequest request,HttpServletResponse response){
        TaotaoResult result = cartService.updateCartItemNum(itemId, num, request, response);
        return result;
    }

    //删除购物车商品
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        TaotaoResult result = cartService.deleteCartItem(itemId, request, response);
        return "redirect:/cart/cart.html";
    }

}
