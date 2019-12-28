package com.ky.ulearning.common.core.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Request 相关操作
 *
 * @author luyuhao
 * @date 19/12/05 02:36
 */
public class RequestHolderUtil {
    private static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getHttpServletRequest() {
        return Optional.ofNullable(getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .orElse(null);
    }

    public static String getHeaderByName(String name) {
        return Optional.ofNullable(getHttpServletRequest())
                .map(request -> request.getHeader(name))
                .orElse(null);
    }

}
