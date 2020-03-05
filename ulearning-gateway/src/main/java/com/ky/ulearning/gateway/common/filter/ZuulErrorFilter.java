package com.ky.ulearning.gateway.common.filter;

import com.ky.ulearning.common.core.constant.MicroErrorCodeEnum;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_ERROR_FILTER_ORDER;

/**
 * 网关异常拦截处理
 *
 * @author luyuhao
 * @since 2019/12/19 9:35
 */
@Slf4j
@Component
public class ZuulErrorFilter extends SendErrorFilter {
    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_ERROR_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return getRequestContext().getThrowable() != null;
    }

    @Override
    public Object run() {
        RequestContext ctx = getRequestContext();
        if (ctx.getThrowable() != null) {
            Throwable throwable = ctx.getThrowable();

            //阻止SendErrorFilter
            ctx.set(SEND_ERROR_FILTER_RAN, true);

            errorInfo();
        }
        return null;
    }

    /**
     * 访问异常时进行response处理，给予提示
     */
    private void errorInfo() {
        RequestContext ctx = getRequestContext();
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = null;
        try {
            writer = ctx.getResponse().getWriter();
            writer.print(JsonUtil.toJsonString(JsonResult.buildErrorEnum(MicroErrorCodeEnum.SERVER_DOWN)));
        } catch (IOException e) {
            log.error("设置errorInfo异常");
        }
        if (writer != null) {
            writer.close();
        }
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
}
