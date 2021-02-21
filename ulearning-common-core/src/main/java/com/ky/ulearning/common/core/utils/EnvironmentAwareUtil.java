package com.ky.ulearning.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.util.StringUtils;

/**
 * @author luyuhao
 * @date 2019/12/4 18:38
 */
@Slf4j
public class EnvironmentAwareUtil {

    public static void adjust() {
        String path = System.getenv("ulearning");
        String ulearningSecret = System.getenv("ulearningSecret");
        if (StringUtils.isEmpty(path)) {
            path = "local";
        }
        log.info("当前环境为 {}", path);
        log.info("系统密钥为 {}", ulearningSecret);
        System.setProperty(ConfigFileApplicationListener.CONFIG_LOCATION_PROPERTY, "classpath:/config/" + path + "/");
        System.setProperty(ConfigFileApplicationListener.ACTIVE_PROFILES_PROPERTY, path);
        System.setProperty("jasypt.encryptor.password", ulearningSecret);
    }
}
