package com.taotao.portal.service;

import com.taotao.pojo.TbItem;

/**
 * Created by Administrator on 2017/4/24.
 */
public interface ItemService {
    TbItem getTbItemById(Long itemId);
    String getItemDescById(Long itemId);
    String getItemParamItemById(Long itemId) throws Exception;
}
