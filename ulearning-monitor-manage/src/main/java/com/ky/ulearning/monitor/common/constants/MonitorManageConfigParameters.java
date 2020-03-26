package com.ky.ulearning.monitor.common.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author luyuhao
 * @since 2020/03/27 00:52
 */
@Getter
@Component
public class MonitorManageConfigParameters {

    @Value("${spring.application.name}")
    private String appName;
}
