package com.taotao.portal;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录拦截器
 * Created by Administrator on 2017/4/30.
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${SSO_LOGIN_URL}")
    private String SSO_LOGIN_URL;

    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、拦截请求url
        // 2、从cookie中取token
        // 3、如果没有toke跳转到登录页面。
        // 4、取到token，需要调用sso系统的服务查询用户信息。
        TbUser tbUser = userService.getUserByToken(request, response);
        // 5、如果用户session已经过期，跳转到登录页面
        if(tbUser == null){
            response.sendRedirect(SSO_LOGIN_URL +"?redirectURL=" +request.getRequestURL());
            return false;
        }
        //把用户对象放入request中
        request.setAttribute("user",tbUser);
        // 6、如果没有过期，放行。
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
