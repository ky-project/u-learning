package com.ky.ulearning.common.core.utils;

import org.springframework.util.DigestUtils;

/**
 * 加密工具类
 *
 * @author luyuhao
 * @date 19/12/06 03:33
 */
public class EncryptUtil {

    /**
     * 密码加密
     *
     * @param password 密码
     * @return 返回加密后的密码串
     */
    public static String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }
}
