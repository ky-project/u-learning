package com.ky.ulearning.gateway.common.utils;

import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.gateway.common.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author luyuhao
 * @since 20/02/07 00:04
 */
@Slf4j
@Component
public class SendMailUtil {

    /**
     * 验证码邮件格式
     */
    private static final String VERIFY_CODE_MAIL = "<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">\n" +
            "    <tbody>\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">\n" +
            "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">\n" +
            "                    <tbody>\n" +
            "                    <tr>\n" +
            "                        <td width=\"210\">\n" +
            "                           \n" +
            "                        </td>\n" +
            "                    </tr>\n" +
            "                    </tbody>\n" +
            "                </table>\n" +
            "            </div>\n" +
            "            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">\n" +
            "                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">\n" +
            "                    <strong style=\"display:block;margin-bottom:15px;\">\n" +
            "                        亲爱的用户：\n" +
            "                        <span style=\"color:#f60;font-size: 16px;\">%s</span>您好！\n" +
            "                    </strong>\n" +
            "\n" +
            "                    <strong style=\"display:block;margin-bottom:15px;\">\n" +
            "                        您正在修改登录密码，请在验证码输入框中输入：\n" +
            "                        <span style=\"color:#f60;font-size: 24px\"><span style=\"border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;\" t=\"7\" onclick=\"return false;\" data=\"898009\">%s</span></span>，以完成操作。\n" +
            "                    </strong>\n" +
            "                </div>\n" +
            "\n" +
            "                <div style=\"margin-bottom:30px;\">\n" +
            "                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">\n" +
            "                        <p style=\"color:#747474;\">\n" +
            "                            注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证帐户安全\n" +
            "                            <br>（工作人员不会向你索取此验证码，请勿泄漏！)\n" +
            "                        </p>\n" +
            "                    </small>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div style=\"width:700px;margin:0 auto;\">\n" +
            "                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n" +
            "                    <p>此为系统邮件，请勿回复<br>\n" +
            "                        请保管好您的邮箱，避免账号被他人盗用\n" +
            "                    </p>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    </tbody>\n" +
            "</table>";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Async
    public void sendVerifyCodeMail(String username, String code, String sendTo) {
        String tmpMailContent = VERIFY_CODE_MAIL;
        tmpMailContent = String.format(tmpMailContent, username, code);
        MimeMessage message;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(defaultConfigParameters.getMailFrom());
            // 接收地址
            helper.setTo(sendTo);
            // 标题
            helper.setSubject(GatewayConstant.VERIFY_CODE_MAIL_TITLE);
            helper.setText(tmpMailContent, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
