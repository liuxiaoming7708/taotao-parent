package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

/**
 * Created by Administrator on 2017/4/19.
 */
public interface SearchService {
    SearchResult search(String keyword,int page,int rows);
}
