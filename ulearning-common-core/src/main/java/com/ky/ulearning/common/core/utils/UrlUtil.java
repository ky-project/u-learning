package com.ky.ulearning.common.core.utils;

import org.springframework.util.AntPathMatcher;

/**
 * @author luyuhao
 * @date 19/12/14 21:44
 */
public class UrlUtil {

    private static AntPathMatcher matcher = new AntPathMatcher();

    /**
     * 判断path是否在需要校验patterns中
     * 若patterns为空，则默认为false
     *
     * @param path     访问path
     * @param patterns 待校验patterns
     * @return true:是; false:否
     */
    public static boolean matchUri(String path, String... patterns) {
        if (patterns == null) {
            return false;
        }
        for (String pattern : patterns) {
            if (matcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }
}
