package com.taotao.order.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;

/**
 * Created by Administrator on 2017/5/2.
 */
public interface OrderService {
    TaotaoResult creatOrder(OrderInfo orderInfo);
}
