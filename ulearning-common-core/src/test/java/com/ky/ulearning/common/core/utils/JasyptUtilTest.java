package com.ky.ulearning.common.core.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 加密测试类
 *
 * @author luyuhao
 * @date 2021/09/10 21:35
 */
public class JasyptUtilTest {

    private static final String secret = "xxx";

    @Test
    public void test01() {
        List<String> list = new ArrayList<>();

        for (String str : list) {
            System.out.println(str + " -> " + JasyptUtil.defaultEncrypt(str, secret));
        }
    }

    @Test
    public void test02() {
        List<String> list = new ArrayList<>();

        for (String str : list) {
            System.out.println(str + " -> " + JasyptUtil.defaultDecrypt(str, secret));
        }
    }
}
