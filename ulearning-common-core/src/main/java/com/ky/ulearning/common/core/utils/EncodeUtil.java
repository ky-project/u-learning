package com.ky.ulearning.common.core.utils;

import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码格式工具类
 *
 * @author luyuhao
 * @since 2020/3/14 14:07
 */
@Slf4j
public class EncodeUtil {

    private static final String APP_ENCODING = "UTF-8";

    private static final String DB_ENCODING = "UTF-8";

    /**
     * 根据应用的字符集对字符串进行URL编码
     */
    public static String encodeURL(Object object) {
        if (object == null) {
            return "";
        }
        try {
            return URLEncoder.encode(object.toString(), APP_ENCODING);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * 根据应用的字符集对字符串进行URL解码
     */
    public static String decodeURL(Object object) {
        if (object == null) {
            return "";
        }
        try {
            return URLDecoder.decode(object.toString(), APP_ENCODING);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException(e.getMessage());
        }
    }
}
