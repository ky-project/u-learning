package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.entity.ActivityEntity;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;
import com.ky.ulearning.spi.student.entity.StudentExaminationTaskEntity;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.student.dao.*;
import com.ky.ulearning.student.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author luyuhao
 * @since 2020/04/03 00:20
 */
@Service
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class ActivityServiceImpl extends BaseService implements ActivityService {

    private static final String COMPLETE_EXAMINATION_TOPIC = "学号：{0}同学 完成测试《{1}》";

    private static final String COMPLETE_EXAMINATION_CONTENT = "学号：{0}同学 完成 [{1}]{2} 课程的测试《{3}》，可查阅该学生的测试结果";

    private static final String COMPLETE_EXPERIMENT_TOPIC = "学号：{0}同学 完成实验《{1}》";

    private static final String COMPLETE_EXPERIMENT_CONTENT = "学号：{0}同学 完成 [{1}]{2} 课程的实验《{3}》，请及时阅读并批改改学生的实验结果";

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private StudentExaminationTaskDao studentExaminationTaskDao;

    @Autowired
    private ExaminationTaskDao examinationTaskDao;

    @Autowired
    private TeachingTaskDao teachingTaskDao;

    @Autowired
    private ExperimentResultDao experimentResultDao;

    @Autowired
    private TeachingTaskExperimentDao teachingTaskExperimentDao;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void completeExaminationActivity(Long examiningId, String username) {
        //获取学生测试信息
        StudentExaminationTaskEntity studentExaminationTaskEntity = studentExaminationTaskDao.getById(examiningId);
        //获取测试任务信息
        ExaminationTaskEntity examinationTaskEntity = examinationTaskDao.getById(studentExaminationTaskEntity.getExaminationTaskId());
        //获取教学任务信息
        TeachingTaskEntity teachingTaskEntity = teachingTaskDao.getById(examinationTaskEntity.getTeachingTaskId());

        //初始化动态信息

        String activityTopic = MessageFormat.format(COMPLETE_EXAMINATION_TOPIC, username, examinationTaskEntity.getExaminationName());
        String activityContent = MessageFormat.format(COMPLETE_EXAMINATION_CONTENT,
                username,
                teachingTaskEntity.getTerm(),
                teachingTaskEntity.getTeachingTaskAlias(),
                examinationTaskEntity.getExaminationName());


        ActivityEntity activityEntity = ActivityEntity.build(username, username, teachingTaskEntity.getTeaId().toString(), activityTopic, activityContent, MicroConstant.SYS_TYPE_STUDENT, null);
        activityDao.insert(activityEntity);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void completeExperimentActivity(Long experimentResultId, String username) {
        //获取学生实验信息
        ExperimentResultEntity experimentResultEntity = experimentResultDao.getById(experimentResultId);
        //获取实验信息
        TeachingTaskExperimentDto teachingTaskExperimentDto = teachingTaskExperimentDao.getById(experimentResultEntity.getExperimentId());
        //获取教学任务信息
        TeachingTaskEntity teachingTaskEntity = teachingTaskDao.getById(teachingTaskExperimentDto.getTeachingTaskId());
        //初始化动态信息

        String activityTopic = MessageFormat.format(COMPLETE_EXPERIMENT_TOPIC, username, teachingTaskExperimentDto.getExperimentTitle());
        String activityContent = MessageFormat.format(COMPLETE_EXPERIMENT_CONTENT,
                username,
                teachingTaskEntity.getTerm(),
                teachingTaskEntity.getTeachingTaskAlias(),
                teachingTaskExperimentDto.getExperimentTitle());

        ActivityEntity activityEntity = ActivityEntity.build(username, username, teachingTaskEntity.getTeaId().toString(), activityTopic, activityContent, MicroConstant.SYS_TYPE_STUDENT, null);
        activityDao.insert(activityEntity);
    }

    @Override
    public PageBean<ActivityEntity> pageList(PageParam pageParam, Long stuId) {
        List<ActivityEntity> resultList = activityDao.listPage(stuId, pageParam);
        Integer total = activityDao.countListPage(stuId);
        return createPageBean(pageParam, total, resultList);
    }
}
