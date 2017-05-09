package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId);

}
