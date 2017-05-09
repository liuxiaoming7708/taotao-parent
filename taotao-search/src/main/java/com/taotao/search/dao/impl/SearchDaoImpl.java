package com.taotao.search.dao.impl;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchItem;
import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * solr查询dao
 * Created by Administrator on 2017/4/19.
 */
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrServer solrServer;
    @Override
    public SearchResult search(SolrQuery solrQuery) throws Exception {
        //执行查询
        QueryResponse queryResponse = solrServer.query(solrQuery);
        //去查询结果列表
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        List<SearchItem> searchItemList = new ArrayList<>();
        for (SolrDocument solrDocument:solrDocumentList) {
            //创建一个SearchItem对象
            SearchItem searchItem = new SearchItem();
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            searchItem.setId((String) solrDocument.get("id"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setPrice((Long) solrDocument.get("item_price"));
            searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            //取高亮后的结果
            String itemTitle = "";
            if(null != list && 0 < list.size()){
                itemTitle = list.get(0);
            }else{
                itemTitle = (String) solrDocument.get("item_title");
            }
            searchItem.setTitle(itemTitle);
            //添加到列表
            searchItemList.add(searchItem);
        }
        SearchResult result = new SearchResult();
        result.setItemList(searchItemList);
        //查询结果总数量
        result.setRecordCount(solrDocumentList.getNumFound());
        return result;
    }
}
