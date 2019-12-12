package com.ky.ulearning.gateway.common.filter;

import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.gateway.common.constant.GatewayConstant;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import com.ky.ulearning.gateway.common.security.JwtAccount;
import com.ky.ulearning.gateway.common.util.JwtAccountUtil;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author luyuhao
 * @date 19/12/12 23:27
 */
@Slf4j
@Component
public class PermissionZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        log.info("{} request uri : {}", JwtAccountUtil.getUsername(), getRequest().getRequestURI());
        return getRequest().getRequestURI().startsWith(MicroConstant.SYSTEM_MANAGE_URI_PREFIX);
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = getRequestContext();
        HttpServletResponse response = getResponse();
        //只有教师可以访问后台,且必须有访问该接口的权限
        if(!GatewayConstant.SYS_ROLE_TEACHER.equals(JwtAccountUtil.getSysRole()) || insufficientPermission()){
            insufficientPermissionHandle();
            return null;
        }
        return null;
    }

    /**
     * 是否无权限
     * @return 无:true; 有:false
     */
    private boolean insufficientPermission(){
        String requestUri = getRequest().getRequestURI();
        List<PermissionEntity> permissions = JwtAccountUtil.getPermissions();
        if(permissions == null){
            return true;
        }
        for (PermissionEntity permission : permissions) {
            if(permission.getPermissionUrl().equals(requestUri)){
                return false;
            }
        }
        return true;
    }

    /**
     * 无权限时进行response处理，给予提示
     */
    private void insufficientPermissionHandle(){
        RequestContext ctx = getRequestContext();
        HttpServletResponse response = getResponse();
        ctx.setSendZuulResponse(false);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ctx.setResponse(response);
        ctx.setResponseBody(JsonUtil.toJsonString(new JsonResult<>(GatewayErrorCodeEnum.INSUFFICIENT_PERMISSION)));
    }

    private RequestContext getRequestContext(){
        return RequestContext.getCurrentContext();
    }

    private HttpServletRequest getRequest(){
        return getRequestContext().getRequest();
    }

    private HttpServletResponse getResponse(){
        return getRequestContext().getResponse();
    }
}
