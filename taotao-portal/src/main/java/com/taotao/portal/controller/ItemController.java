package com.taotao.portal.controller;

import com.taotao.pojo.TbItem;
import com.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 查询商品信息controller
 * Created by Administrator on 2017/4/24.
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    //查询商品基本信息
    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId,Model model){
        TbItem tbItem = itemService.getTbItemById(itemId);
        model.addAttribute("item",tbItem);
        return "item";
    }
    //查询商品描述信息
    @RequestMapping(value ="item/desc/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemDesc(@PathVariable Long itemId){
        String itemDesc = itemService.getItemDescById(itemId);
        return itemDesc;
    }
    //查询商品规格参数
    @RequestMapping(value="item/param/{itemId}",produces = MediaType.TEXT_HTML_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemParam(@PathVariable Long itemId) throws Exception {
        String itemParamItem = itemService.getItemParamItemById(itemId);
        return itemParamItem;
    }
}
