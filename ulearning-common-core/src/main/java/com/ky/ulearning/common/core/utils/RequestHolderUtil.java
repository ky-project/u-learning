package com.ky.ulearning.common.core.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Request 相关操作
 *
 * @author luyuhao
 * @since 19/12/05 02:36
 */
public class RequestHolderUtil {
    /**
     * 获取请求上下文
     */
    private static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取请求域
     */
    public static HttpServletRequest getHttpServletRequest() {
        return Optional.ofNullable(getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .orElse(null);
    }

    /**
     * 获取请求头
     */
    public static String getHeaderByName(String name) {
        return Optional.ofNullable(getHttpServletRequest())
                .map(request -> request.getHeader(name))
                .orElse(null);
    }

    /**
     * 获取请求属性
     */
    public static <T> T getAttribute(String name, Class<T> clazz) {
        try {
            Object object = Optional.ofNullable(getHttpServletRequest())
                    .map(request -> request.getAttribute(name))
                    .orElse(null);
            return Optional.ofNullable(object)
                    .map(o -> JsonUtil.parseObjectByObject(object, clazz))
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
