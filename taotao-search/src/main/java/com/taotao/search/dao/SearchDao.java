package com.taotao.search.dao;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * Created by Administrator on 2017/4/19.
 */
public interface SearchDao {
    SearchResult search(SolrQuery solrQuery) throws Exception;
}
