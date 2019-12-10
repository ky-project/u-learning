package com.ky.ulearning.gateway.common.security;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 用户没有权限访问某个资源的时候，可以在这里自定义返回内容
 *
 * @author luyuhao
 * @date 2019/12/10 9:43
 */
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        //当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
        JsonResult jsonResult = new JsonResult<>(GatewayErrorCodeEnum.NOT_LOGGED_IN);
        String jsonString = JSONObject.toJSONString(jsonResult, SerializerFeature.WriteMapNullValue);
        response.setContentType("text/json;charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(jsonString);
    }
}
