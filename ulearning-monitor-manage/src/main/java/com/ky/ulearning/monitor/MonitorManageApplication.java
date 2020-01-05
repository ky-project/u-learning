package com.ky.ulearning.monitor;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author luyuhao
 * @date 19/12/05 02:07
 */
@SpringBootApplication(scanBasePackages = {
        "com.ky.ulearning.monitor",
        "com.ky.ulearning.common.core.component"})
@EnableEurekaClient
@EnableAsync
@EnableAdminServer
public class MonitorManageApplication {
    public static void main(String[] args) {
        EnvironmentAwareUtil.adjust();
        SpringApplication.run(MonitorManageApplication.class, args);
    }
}
