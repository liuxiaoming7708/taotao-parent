package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * Created by Administrator on 2017/4/26.
 */
public interface RegisterService {
    TaotaoResult checkData(String param,int type);
    TaotaoResult register(TbUser tbUser);
}
