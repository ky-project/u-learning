package com.ky.ulearning.monitor.logging.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Request 相关操作
 *
 * @author luyuhao
 * @date 19/12/05 02:36
 */
public class RequestHolderUtil {
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
