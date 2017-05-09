package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * Created by Administrator on 2017/3/21.
 */
public interface ItemService {

    TbItem getItemById(Long itemId);

    EasyUIDataGridResult getItemList(int page,int rows);

    TaotaoResult createItem(TbItem tbItem,String desc,String itemParam);

    String getItemParamHtml(Long itemId);

}
