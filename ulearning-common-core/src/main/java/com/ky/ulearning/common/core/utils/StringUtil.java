package com.ky.ulearning.common.core.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作
 *
 * @author luyuhao
 * @date 19/12/15 14:40
 */
public class StringUtil extends StringUtils {

    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\u4e00-\u9fa5]");

    public static boolean isContainChinese(String str) {
        if(isEmpty(str)){
            return false;
        }
        Matcher m = CHINESE_PATTERN.matcher(str);
        return m.find();
    }
}
