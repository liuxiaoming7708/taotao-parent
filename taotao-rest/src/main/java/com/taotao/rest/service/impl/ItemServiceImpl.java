package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.*;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品管理service
 * Created by Administrator on 2017/4/24.
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;
    @Value("${ITEM_BASE_INFO_KEY}")
    private String ITEM_BASE_INFO_KEY;
    @Value("${ITEM_EXPIRE_SECOND}")
    private Integer ITEM_EXPIRE_SECOND;
    @Value("${ITEM_DESC_KEY}")
    private String ITEM_DESC_KEY;
    @Value("${ITEM_PARAM_KEY}")
    private String ITEM_PARAM_KEY;
    //查询商品基本信息
    @Override
    public TbItem getItemById(Long itemId) {
      	//查询缓存，如果有缓存，直接返回
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_BASE_INFO_KEY);
           //判断缓存中的数据是否存在
            if(StringUtils.isNotBlank(json)){
                // 把json数据转换成java对象
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //查询数据库（根据商品id查询商品基本信息）
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);

        //向redis中添加缓存。
        //添加缓存原则是不能影响正常的业务逻辑
        try {
            //向redis中添加缓存。
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_BASE_INFO_KEY,
                    JsonUtils.objectToJson(tbItem));
            //设置key的过期时间
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_BASE_INFO_KEY,
                    ITEM_EXPIRE_SECOND);
        }catch (Exception e){
                e.printStackTrace();
        }
        return tbItem;
    }
    //查询商品描述信息
    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        //查询缓存，如果有缓存，直接返回
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_KEY);
            //判断缓存中的数据是否存在
            if(StringUtils.isNotBlank(json)){
                // 把json数据转换成java对象
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return tbItemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //查询数据库（根据商品ID查询商品描述信息）
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

        //向redis中添加缓存。
        //添加缓存原则是不能影响正常的业务逻辑
        try {
            //向redis中添加缓存。
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_KEY,
                    JsonUtils.objectToJson(tbItemDesc));
            //设置key的过期时间
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_DESC_KEY,
                    ITEM_EXPIRE_SECOND);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tbItemDesc;
    }
    //查询商品的规格参数
    @Override
    public TbItemParamItem getItemParamItemById(Long itemId) {
        //添加缓存逻辑
        //查询缓存，如果有缓存，直接返回
        try {
            String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_PARAM_KEY);
            //判断缓存中的数据是否存在
            if(StringUtils.isNotBlank(json)){
                // 把json数据转换成java对象
                TbItemParamItem tbItemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return tbItemParamItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        //查询数据库（根据商品ID查询商品规格参数）
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItemList = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        //判断商品规格参数是否存在
        if(null != tbItemParamItemList && 0 < tbItemParamItemList.size()){
            TbItemParamItem tbItemParamItem = tbItemParamItemList.get(0);

            //向redis中添加缓存。
            //添加缓存原则是不能影响正常的业务逻辑
            try {
                //向redis中添加缓存。
                jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_PARAM_KEY,
                        JsonUtils.objectToJson(tbItemParamItem));
                //设置key的过期时间
                jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":"+ITEM_PARAM_KEY,
                        ITEM_EXPIRE_SECOND);
            }catch (Exception e){
                e.printStackTrace();
            }
            return tbItemParamItem;
        }
        return null;
    }
}
