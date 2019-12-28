package com.ky.ulearning.gateway.common.filter;

import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.IpUtil;
import com.ky.ulearning.gateway.common.utils.JwtAccountUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luyuhao
 * @date 19/12/16 00:30
 */
@Slf4j
@Component
public class HeaderZuulFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = getRequestContext();
        //设置ip和用户账号于请求头中
        ctx.addZuulRequestHeader(MicroConstant.USER_REQUEST_IP, IpUtil.getIP(getRequest()));
        ctx.addZuulRequestHeader(MicroConstant.USERNAME, JwtAccountUtil.getUsername());
        return null;
    }

    private RequestContext getRequestContext() {
        return RequestContext.getCurrentContext();
    }

    private HttpServletRequest getRequest() {
        return getRequestContext().getRequest();
    }
}
