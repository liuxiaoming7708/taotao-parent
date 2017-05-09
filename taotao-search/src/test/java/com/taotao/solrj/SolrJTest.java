package com.taotao.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * Created by Administrator on 2017/4/12.
 */
public class SolrJTest {
    @Test
    public void testSolrJ() throws Exception{
        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://172.30.101.129:8080/solr");
        //创建一个文档对象
        SolrInputDocument  document = new SolrInputDocument();
        //添加域
        document.addField("id","solrTest01");
        document.addField("item_title","测试产品");
        document.addField("item_sell_point","卖点");
        //添加索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }
    @Test
    public void testQuery() throws Exception{
        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://172.30.101.129:8080/solr");
        //创建一个查询对象
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        //执行查询
        QueryResponse response = solrServer.query(query);
        //去查询结果
        SolrDocumentList solrDocumentList = response.getResults();
        for (SolrDocument solrDocument : solrDocumentList){
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));
        }
    }
}
