package com.taotao.web.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.web.bean.User;
import com.taotao.web.service.UserService;
import com.taotao.web.threadlocal.UserThreadLocal;

public class UserLoginHandlerInterceptor implements HandlerInterceptor{
    
    @Autowired
    private UserService userService;
    
    public static final String COOKIE_NAME = "TT_TOKEN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        UserThreadLocal.set(null);//清空本地线程中的User对象数据
                                  //注：因为每个请求都是一个独立的线程，但是因为考虑到性能问题，
                                  //Tomcat底层使用线程池，线程并不会真正的销毁，而会被复用，
                                  //这就有可能存在其它请求复用该线程对象时，获取到该线程的ThreadLocal历史数据的情况，因此在使用前要先置空
        
        String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
        
        if (StringUtils.isNotEmpty(token)) {
            User user = this.userService.queryByToken(token);
            if (user != null) {
                //登录成功
                UserThreadLocal.set(user);//将User对象放置到本地线程中，方便在Controller和Service中获取
                return true;
            }
        }
        //未登录或登录超时，跳转到登录页面
        String loginUrl = this.userService.TAOTAO_SSO_URL + "/user/login.html";
        response.sendRedirect(loginUrl);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        UserThreadLocal.set(null);//改进的ThreadLocal使用方式
    }

}
