package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.entity.ActivityEntity;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.teacher.dao.ActivityDao;
import com.ky.ulearning.teacher.dao.StudentTeachingTaskDao;
import com.ky.ulearning.teacher.dao.TeachingTaskDao;
import com.ky.ulearning.teacher.service.ActivityService;
import com.ky.ulearning.teacher.service.ExaminationTaskService;
import com.ky.ulearning.teacher.service.SendEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    private static final String CREATE_EXAMINATION_TASK_TOPIC = "您有新的测试任务-{0}，请关注测试信息";

    private static final String CREATE_EXAMINATION_TASK_CONTENT = " {1} 学期，{0} 老师的 {2} 课程发布了新的测试任务 {3}，测试时间 {4} 分钟，详情请登录U-Learning平台查阅";

    @Autowired
    private ExaminationTaskService examinationTaskService;

    @Autowired
    private TeachingTaskDao teachingTaskDao;

    @Autowired
    private StudentTeachingTaskDao studentTeachingTaskDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private SendEmailService sendEmailService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void createExaminationTask(Long examinationTaskId) {
        //获取测试任务对象
        ExaminationTaskEntity examinationTaskEntity = examinationTaskService.getById(examinationTaskId);
        TeachingTaskDto teachingTaskDto = teachingTaskDao.getInfoById(examinationTaskEntity.getTeachingTaskId());
        //查询选课学生信息
        List<StudentEntity> studentEntityList = Optional.ofNullable(studentTeachingTaskDao.getStudentListByTeachingTaskId(examinationTaskEntity.getTeachingTaskId())).orElse(Collections.emptyList());
        //获取动态基本信息
        StringBuilder userIds = new StringBuilder();
        StringBuilder activityEmail = new StringBuilder();
        for (StudentEntity studentEntity : studentEntityList) {
            userIds.append(studentEntity.getId()).append(",");
            if (StringUtil.isNotEmpty(studentEntity.getStuEmail())) {
                activityEmail.append(studentEntity.getStuEmail()).append(",");
            }
        }
        if (userIds.lastIndexOf(",") != -1
                && userIds.lastIndexOf(",") == userIds.length() - 1) {
            userIds.deleteCharAt(userIds.length() - 1);
        }
        if (activityEmail.lastIndexOf(",") != -1
                && activityEmail.lastIndexOf(",") == activityEmail.length() - 1) {
            activityEmail.deleteCharAt(activityEmail.length() - 1);
        }
        String activityTopic = MessageFormat.format(CREATE_EXAMINATION_TASK_TOPIC, examinationTaskEntity.getExaminationName());
        String activityContent = MessageFormat.format(CREATE_EXAMINATION_TASK_CONTENT,
                teachingTaskDto.getTeaName(),
                teachingTaskDto.getTerm(),
                teachingTaskDto.getTeachingTaskAlias(),
                examinationTaskEntity.getExaminationName(),
                examinationTaskEntity.getExaminationDuration());
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);

        //创建动态信息
        ActivityEntity activityEntity = ActivityEntity.build(username, username, userIds.toString(), activityTopic, activityContent, MicroConstant.SYS_TYPE_TEACHER, activityEmail.toString());
        //处理动态信息
        insertTeacherActivity(activityEntity);
    }

    /**
     * 插入教师动态
     */
    private void insertTeacherActivity(ActivityEntity activityEntity) {
        //1.获取推送邮箱
        String activityEmails = activityEntity.getActivityEmail();
        //2. 发送邮件
        if (StringUtil.isNotEmpty(activityEmails)) {
            sendEmailService.batchSendActivityEmail(activityEntity, TEACHER_EMAIL_TEMPLATE);
        }
        //3. 保存动态记录
        activityDao.insert(activityEntity);
    }
}
