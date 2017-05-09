package com.taotao.portal.controller;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单处理
 * Created by Administrator on 2017/5/2.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request, Model model){
        List<CartItem> cartItemList = cartService.getCartItems(request);
        model.addAttribute("cartList",cartItemList);
        return "order-cart";
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model,
                              HttpServletRequest request){
        //取用户信息
        TbUser tbUser = (TbUser) request.getAttribute("user");
        //补全orderIn的属性
        orderInfo.setUserId(tbUser.getId());
        orderInfo.setBuyerNick(tbUser.getUsername());
        //调用服务
        String orderId = orderService.createOrder(orderInfo);
        //把订单号传递给页面
        model.addAttribute("orderId",orderId);
        model.addAttribute("payment",orderInfo.getPayment());
        // DateTime dateTime = new DateTime();
        // dateTime = dateTime.plusDays(3);
        model.addAttribute("date",new DateTime().plusDays(3).toString("yyyy-MM-dd"));
        return "success";
    }
}
