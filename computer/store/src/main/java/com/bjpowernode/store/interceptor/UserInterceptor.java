package com.bjpowernode.store.interceptor;

import com.bjpowernode.store.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //编写业务拦截的规则
        //从session中获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        //判断用户是否登录
        if (user==null){
            //未登录
            response.sendRedirect(request.getContextPath()+"/web/login.html");
            return false;
        }

        return true;
    }
}
