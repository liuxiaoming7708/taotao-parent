package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.RegisterService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户注册service
 * 数据校验
 * Created by Administrator on 2017/4/26.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper tbUserMapper;
    //数据校验
    @Override
    public TaotaoResult checkData(String param, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //根据数据类型检查数据
        //1、2、3分别代表username、phone、email
        if(1 == type){
            criteria.andUsernameEqualTo(param);
        }else if(2 == type){
            criteria.andPhoneEqualTo(param);
        }else if(3 == type){
            criteria.andEmailEqualTo(param);
        }
        //执行查询
        List<TbUser> tbUserList = tbUserMapper.selectByExample(example);
        //判断查询结果是否为空
        if(null == tbUserList || tbUserList.isEmpty()){
                return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    //用户注册
    @Override
    public TaotaoResult register(TbUser tbUser) {
        // 校验数据
        //校验用户名、密码不能为空
        if(StringUtils.isBlank(tbUser.getUsername())
                ||StringUtils.isBlank(tbUser.getPassword())){
            return TaotaoResult.build(400,"用户名或密码不能为空");
        }
        //校验数据是否重复
        //校验用户名
        TaotaoResult result = checkData(tbUser.getUsername(), 1);
        if(!(Boolean) result.getData()){
            return TaotaoResult.build(400,"用户名重复");
        }
        //校验手机号
        if(null !=tbUser.getPhone()){
            result = checkData(tbUser.getPhone(),2);
            if(!(Boolean) result.getData()){
                return TaotaoResult.build(400,"手机号重复");
            }
        }
        //校验邮箱
        if(null != tbUser.getEmail()){
            result = checkData(tbUser.getEmail(),3);
            if(!(Boolean) result.getData()){
                return TaotaoResult.build(400,"邮箱重复");
            }
        }
        //插入数据
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        //密码进行MD5加密
        tbUser.setPassword(DigestUtils.md5Hex(tbUser.getPassword()));
        tbUserMapper.insert(tbUser);
        return TaotaoResult.ok();
    }
}
