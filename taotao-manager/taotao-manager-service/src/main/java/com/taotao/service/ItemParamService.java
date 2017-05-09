package com.taotao.service;

import com.sun.tools.javac.util.List;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by Administrator on 2017/3/28.
 */
public interface ItemParamService {
    TaotaoResult getItemParamByCid(Long cid);

    EasyUIDataGridResult getItemParamList(int page,int rows);

    TaotaoResult insertItemParam(Long cid,String paramData);

    TaotaoResult deleteItemParam(Long[] ids);

}
