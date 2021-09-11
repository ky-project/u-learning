package com.ky.ulearning.common.core.utils;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

/**
 * Jasypt加密工具类
 *
 * @author luyuhao
 * @since 2020/08/11 00:33
 */
public class JasyptUtil {

    private static final String PBEWITHMD5ANDDES = "PBEWithMD5AndDES";

    private static final String PBEWITHHMACSHA512ANDAES_256 = "PBEWITHHMACSHA512ANDAES_256";

    /**
     * 默认加密
     *
     * @param str    待加密原文
     * @param secret 密钥
     * @return 加密串
     * @author luyuhao
     * @since 20/08/11 00:33
     */
    public static String defaultEncrypt(String str, String secret) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(createDefaultConfig(secret));
        return encryptor.encrypt(str);
    }

    /**
     * 默认解密
     *
     * @param str    待加密原文
     * @param secret 密钥
     * @return 解密串
     * @author luyuhao
     * @since 20/08/11 00:33
     */
    public static String defaultDecrypt(String str, String secret) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(createDefaultConfig(secret));
        return encryptor.decrypt(str);
    }

    /**
     * 生成默认config
     *
     * @param secret 密钥
     * @return 默认config
     * @author luyuhao
     * @since 20/08/11 01:14
     */
    private static SimpleStringPBEConfig createDefaultConfig(String secret) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(secret);
        config.setAlgorithm(PBEWITHMD5ANDDES);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName(null);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }
}
