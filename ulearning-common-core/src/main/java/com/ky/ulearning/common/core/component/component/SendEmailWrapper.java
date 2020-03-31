package com.ky.ulearning.common.core.component.component;

import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author luyuhao
 * @since 2020/04/01 01:08
 */
@Slf4j
@Component
public class SendEmailWrapper {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    public void sendHtmlMail(String sendTo, String mailContent, String subject) {
        MimeMessage message;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(defaultConfigParameters.getMailFrom());
            // 接收地址
            helper.setTo(sendTo);
            // 标题
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
