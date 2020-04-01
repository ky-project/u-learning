package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.component.component.SendEmailWrapper;
import com.ky.ulearning.spi.common.entity.ActivityEntity;
import com.ky.ulearning.teacher.service.SendEmailService;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void batchSendActivityEmail(ActivityEntity activityEntity, String templateName) {
        try {
            //收件人
            String[] sendTo = activityEntity.getActivityEmail().split(",");
            String content = "";
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("params", activityEntity);
                Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
                content = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
            } catch (IOException e) {
                log.error("模板 teacherActivityMailTemplate 加载失败，请检查", e);
            }
            sendEmailWrapper.batchSendHtmlMail(sendTo, content, activityEntity.getActivityTopic());
            log.info("发送邮件成功，主题：{}，接收人：{}", activityEntity.getActivityTopic(), activityEntity.getActivityEmail());
        } catch (Exception e) {
            log.error("邮件发送失败", e);
        }
    }
}
