package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

/**
 * Created by Administrator on 2017/4/24.
 */
public interface ItemService {
    TbItem getItemById(Long itemId);
    TbItemDesc getItemDescById(Long itemId);
    TbItemParamItem getItemParamItemById(Long itemId);

 }
