package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理controller
 * Created by Administrator on 2017/4/24.
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    //查询商品基本信息
    @RequestMapping("/base/{itemId}")
    @ResponseBody
    public TaotaoResult getItemById(@PathVariable Long itemId){
        try {
            TbItem item = itemService.getItemById(itemId);
            return TaotaoResult.ok(item);
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }
    //查询商品描述信息
    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDescById(@PathVariable Long itemId){
        try {
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            return TaotaoResult.ok(itemDesc);
        }catch(Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }

    }
    //查询商品规格参数
    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public TaotaoResult getItemParamItemById(@PathVariable Long itemId){
        try {
            TbItemParamItem itemParamItem = itemService.getItemParamItemById(itemId);
            return TaotaoResult.ok(itemParamItem);
        }catch(Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }

    }
}
