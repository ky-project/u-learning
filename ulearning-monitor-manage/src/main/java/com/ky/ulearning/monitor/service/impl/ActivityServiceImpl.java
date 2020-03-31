package com.ky.ulearning.monitor.service.impl;

import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.monitor.dao.ActivityDao;
import com.ky.ulearning.monitor.service.ActivityService;
import com.ky.ulearning.monitor.service.SendEmailService;
import com.ky.ulearning.spi.monitor.entity.ActivityEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 动态service - 实现
 *
 * @author luyuhao
 * @since 2020/04/01 00:05
 */
@Slf4j
@Service
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class ActivityServiceImpl implements ActivityService {

    private static final String TEACHER_EMAIL_TEMPLATE = "teacherActivityMailTemplate.ftl";

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private SendEmailService sendEmailService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void insertTeacherActivity(ActivityEntity activityEntity) {
        //1.获取推送邮箱
        String activityEmails = activityEntity.getActivityEmail();
        //2. 发送邮件
        if (StringUtil.isNotEmpty(activityEmails)) {
            sendEmailService.batchSendActivityEmail(activityEntity, TEACHER_EMAIL_TEMPLATE);
        }
        //3. 保存动态记录
    }
}
