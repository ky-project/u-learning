package com.ky.ulearning.gateway.common.filter;

import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.UrlUtil;
import com.ky.ulearning.gateway.common.constant.GatewayConfigParameters;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import com.ky.ulearning.gateway.common.utils.JwtAccountUtil;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luyuhao
 * @since 19/12/12 23:27
 */
@Slf4j
@Component
public class PermissionZuulFilter extends ZuulFilter {

    @Autowired
    private GatewayConfigParameters gatewayConfigParameters;

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
        String path = getRequest().getRequestURI();
        log.info("{} request uri : {}", JwtAccountUtil.getUsername(), path);
        //访问未放行path时执行
        return !UrlUtil.matchUri(path, gatewayConfigParameters.getReleasePaths());
    }

    @Override
    public Object run() throws ZuulException {

        if (UrlUtil.matchUri(getUri(), gatewayConfigParameters.getAdminPatterns())) {
            //1. 访问后台端服务
            adminPermissionCheck();
        } else if (UrlUtil.matchUri(getUri(), gatewayConfigParameters.getTeacherPatterns())) {
            //2. 访问教师端服务
            teacherPermissionCheck();
        } else if (UrlUtil.matchUri(getUri(), gatewayConfigParameters.getStudentPatterns())) {
            //3. 访问学生端服务
            studentPermissionCheck();
        } else {
            //4. 未知访问
            warnInfo(GatewayErrorCodeEnum.REQUEST_PATH_NOT_REGISTER);
        }
        return null;
    }

    /**
     * 学生端服务权限校验
     */
    private void studentPermissionCheck() {
        String sysRole = JwtAccountUtil.getSysRole();
        if (StringUtils.isEmpty(sysRole)
                || !sysRole.equals(MicroConstant.SYS_ROLE_STUDENT)) {
            warnInfo(GatewayErrorCodeEnum.INSUFFICIENT_PERMISSION);
        }
    }

    /**
     * 教师端服务权限校验
     */
    private void teacherPermissionCheck() {
        String sysRole = JwtAccountUtil.getSysRole();
        if (StringUtils.isEmpty(sysRole)
                || !sysRole.equals(MicroConstant.SYS_ROLE_TEACHER)) {
            warnInfo(GatewayErrorCodeEnum.INSUFFICIENT_PERMISSION);
        }
    }

    /**
     * 后台端服务权限校验
     */
    private void adminPermissionCheck() {
        String sysRole = JwtAccountUtil.getSysRole();
        List<RoleEntity> roles = JwtAccountUtil.getRoles();
        List<PermissionEntity> permissions = JwtAccountUtil.getPermissions();
        //1. 用户系统角色为教师; 有教师角色; 有权限
        if (StringUtils.isEmpty(sysRole)
                || !sysRole.equals(MicroConstant.SYS_ROLE_TEACHER)
                || Collections.isEmpty(roles)
                || Collections.isEmpty(permissions)) {
            warnInfo(GatewayErrorCodeEnum.INSUFFICIENT_PERMISSION);
            return;
        }
        //2. 教师角色中有管理员角色
        if (!roles.stream()
                .map(RoleEntity::getIsAdmin)
                .collect(Collectors.toList())
                .contains(true)) {
            warnInfo(GatewayErrorCodeEnum.INSUFFICIENT_PERMISSION);
            return;
        }
        //3. 有访问该uri的权限
        if (!permissions.stream()
                .map(PermissionEntity::getPermissionUrl)
                .collect(Collectors.toList())
                .contains(getUri())) {
            warnInfo(GatewayErrorCodeEnum.INSUFFICIENT_PERMISSION);
        }
    }

    /**
     * 无权限时进行response处理，给予提示
     */
    private void warnInfo(BaseEnum baseEnum) {
        RequestContext ctx = getRequestContext();
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ctx.setResponseBody(JsonUtil.toJsonString(JsonResult.buildErrorEnum(baseEnum)));
    }

    private RequestContext getRequestContext() {
        return RequestContext.getCurrentContext();
    }

    private HttpServletRequest getRequest() {
        return getRequestContext().getRequest();
    }

    private HttpServletResponse getResponse() {
        return getRequestContext().getResponse();
    }

    private String getUri() {
        return getRequest().getRequestURI();
    }
}
