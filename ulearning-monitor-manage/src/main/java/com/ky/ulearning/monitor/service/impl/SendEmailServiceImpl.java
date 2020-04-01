package com.ky.ulearning.monitor.service.impl;

import com.ky.ulearning.common.core.component.component.SendEmailWrapper;
import com.ky.ulearning.monitor.service.SendEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送邮件service - 实现
 *
 * @author luyuhao
 * @since 2020/04/01 01:17
 */
@Slf4j
@Service
public class SendEmailServiceImpl implements SendEmailService {

    @Autowired
    private SendEmailWrapper sendEmailWrapper;
}
