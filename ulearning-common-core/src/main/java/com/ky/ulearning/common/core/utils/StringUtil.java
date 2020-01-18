package com.ky.ulearning.common.core.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * 判断字符串是否含有中文
     *
     * @param str 待判断的字符串
     * @return true:是; false:否
     */
    public static boolean isContainChinese(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Matcher m = CHINESE_PATTERN.matcher(str);
        return m.find();
    }

    /**
     * 字符串数组转为longList
     *
     * @param strArr 字符串数组
     * @return longList
     */
    public static List<Long> strArrToLongList(String[] strArr) {
        List<Long> res = new ArrayList<>();
        for (String str : strArr) {
            res.add(Long.parseLong(str));
        }
        return res;
    }

    /**
     * 判断对象是否不为空
     *
     * @param str 带判断的对象
     * @return true:不为空;false:为空
     */
    public static boolean isNotEmpty(@Nullable Object str) {
        return !isEmpty(str);
    }
}
