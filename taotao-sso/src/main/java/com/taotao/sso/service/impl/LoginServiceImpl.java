package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.component.JedisClient;
import com.taotao.sso.service.LoginService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * 用户登录服务service
 * Created by Administrator on 2017/4/26.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;
    //用户登录生成token
    @Override
    public TaotaoResult login(String username, String password,
                              HttpServletRequest request, HttpServletResponse response) {
        //校验用户名密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUserList = tbUserMapper.selectByExample(example);
        //取用户信息
        if(null == tbUserList ||tbUserList.isEmpty()){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        TbUser tbUser = tbUserList.get(0);
        //校验密码
        if(!tbUser.getPassword().equals(DigestUtils.md5Hex(password))){
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //登录成功
        //生成token
        String token = UUID.randomUUID().toString();
        //把用户信息写入redis
        //key:REDIS_SESSION:{TOKEN}
        //value:user转json
        tbUser.setPassword(null);
        jedisClient.set(REDIS_SESSION_KEY + ":" + token, JsonUtils.objectToJson(tbUser));
        //设置session的过期时间
        jedisClient.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE);
        //写cookie
        CookieUtils.setCookie(request,response,"TT_TOKEN",token);
        return TaotaoResult.ok(token);
    }

    //通过token从redis中获取用户信息
    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(REDIS_SESSION_KEY + ":" + token);
        //判断是否查询到结果
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"用户session已经过期");
        }
        //把json转换成java对象
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        //更新session的过期时间
        jedisClient.expire(REDIS_SESSION_KEY + ":" + token,SESSION_EXPIRE);
        return TaotaoResult.ok(tbUser);
    }

    //用户安全退出（通过token删除redis中的user信息）
    @Override
    public TaotaoResult logoutByToken(String token) {
        String json = jedisClient.get(REDIS_SESSION_KEY + ":" + token);
        //判断是否过期
        if(StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"该用户session已经过期");
        }
        jedisClient.del(REDIS_SESSION_KEY + ":" + token);
        return TaotaoResult.ok();
    }
}
