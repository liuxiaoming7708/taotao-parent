package com.taotao.rest.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */
public interface ContentService {
    List<TbContent> getContentList(Long cid);

    TaotaoResult syncContent(Long cid);
}
