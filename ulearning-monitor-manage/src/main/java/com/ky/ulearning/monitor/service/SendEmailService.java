package com.ky.ulearning.monitor.service;

import com.ky.ulearning.spi.monitor.entity.ActivityEntity;
import org.springframework.scheduling.annotation.Async;

/**
 * 发送邮件service - 接口
 *
 * @author luyuhao
 * @since 2020/04/01 01:16
 */
public interface SendEmailService {

    /**
     * 群发邮件
     * @param activityEntity 动态对象
     * @param templateName 模板名称
     */
    @Async
    void batchSendActivityEmail(ActivityEntity activityEntity, String templateName);
}
