package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * Created by Administrator on 2017/4/25.
 */
public interface StaticPageService {
    TaotaoResult genItemHtml(Long itemId) throws Exception;
}
