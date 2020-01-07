package com.ky.ulearning.gateway.common.security;

import com.ky.ulearning.common.core.constant.MicroErrorCodeEnum;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.gateway.common.constant.GatewayErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * 自定义返回的错误信息
 *
 * @author luyuhao
 * @date 2019/12/10 9:26
 */
@Slf4j
@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.error("系统捕捉AuthenticationException异常并处理 ==> " + exception.getMessage());
        String message = StringUtil.isContainChinese(exception.getMessage()) ? exception.getMessage() : null;
        JsonResult jsonResult = JsonResult.buildErrorMsg(HttpStatus.UNAUTHORIZED.value(),
                Optional.ofNullable(message).orElse(GatewayErrorCodeEnum.AUTHORIZED_FAILURE.getMessage()));
        String jsonString = JsonUtil.toJsonString(jsonResult);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter out = response.getWriter();
        out.write(jsonString);
        out.close();
    }
}
