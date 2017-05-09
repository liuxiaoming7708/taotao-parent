package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */
public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCatList(Long parentId);
    TaotaoResult insertCategory(Long parentId,String name);
}
