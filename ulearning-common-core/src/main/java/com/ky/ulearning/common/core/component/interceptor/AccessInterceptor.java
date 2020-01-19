package com.ky.ulearning.common.core.component.interceptor;

import com.ky.ulearning.common.core.constant.MicroConstant;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务接受网关传递的登录用户信息拦截器
 *
 * @author luyuhao
 * @since 20/01/19 23:49
 */
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置访问用户名
        String username = request.getHeader(MicroConstant.USERNAME);
        request.setAttribute(MicroConstant.USERNAME, username);
        return true;
    }
}
