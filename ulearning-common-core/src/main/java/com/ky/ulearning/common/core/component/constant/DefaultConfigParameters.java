package com.ky.ulearning.common.core.component.constant;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author luyuhao
 * @date 19/12/17 00:24
 */
@Component
@Getter
public class DefaultConfigParameters {


    @Value("${jwt.header-refresh-token}")
    private String refreshTokenHeader;

    @Value("${jwt.header-token}")
    private String tokenHeader;

    @Value("${swagger.enabled}")
    private Boolean swaggerEnabled;

    @Value("${fdfs.reqHost}")
    private String fdsReqHost;

    @Value("${fdfs.reqPort}")
    private String dfsReqPort;

    @Value("#{${ulearning.file.photo-max-size} * 1024 * 1024}")
    private Long photoMaxSize;

    @Value("#{${ulearning.file.notice-attachment-max-size} * 1024 * 1024}")
    private Long noticeAttachmentMaxSize;

    @Value("#{${ulearning.file.experiment_attachment-max-size} * 1024 * 1024}")
    private Long experimentAttachmentMaxSize;

    @Value("${spring.mail.username}")
    private String mailFrom;
}
