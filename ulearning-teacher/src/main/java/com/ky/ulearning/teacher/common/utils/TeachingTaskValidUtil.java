package com.ky.ulearning.teacher.common.utils;

import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.service.StudentTeachingTaskService;
import com.ky.ulearning.teacher.service.TeacherService;
import com.ky.ulearning.teacher.service.TeachingTaskNoticeService;
import com.ky.ulearning.teacher.service.TeachingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 教学任务有效校验工具类
 *
 * @author luyuhao
 * @since 20/01/30 15:56
 */
@Component
public class TeachingTaskValidUtil {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Autowired
    private StudentTeachingTaskService studentTeachingTaskService;

    @Autowired
    private TeachingTaskNoticeService teachingTaskNoticeService;

    /**
     * 校验教师是否有操作教学任务的权限
     *
     * @param username       教师工号
     * @param teachingTaskId 教学任务id
     */
    public TeacherEntity checkTeachingTask(String username, Long teachingTaskId) {
        TeacherEntity teacherEntity = teacherService.getByTeaNumber(username);
        //教师number是否存在
        ValidateHandler.checkParameter(teacherEntity == null, TeacherErrorCodeEnum.TEA_NUMBER_NOT_EXISTS);
        //教学任务id是否可操作
        Set<Long> teachingTaskIdSet = teachingTaskService.getIdByTeaId(teacherEntity.getId());
        ValidateHandler.checkParameter(teachingTaskIdSet == null || !teachingTaskIdSet.contains(teachingTaskId), TeacherErrorCodeEnum.TEACHING_TASK_ID_ILLEGAL);
        return teacherEntity;
    }

    /**
     * 校验教师是否有操作学生的权限
     *
     * @param username 教师工号
     * @param stuId    学生id
     */
    public TeacherEntity checkStuId(String username, Long stuId) {
        TeacherEntity teacherEntity = teacherService.getByTeaNumber(username);
        //教师number是否存在
        ValidateHandler.checkParameter(teacherEntity == null, TeacherErrorCodeEnum.TEA_NUMBER_NOT_EXISTS);
        //查询可操作的教学任务id
        Set<Long> teachingTaskIdSet = teachingTaskService.getIdByTeaId(teacherEntity.getId());
        Set<Long> stuIdSet = studentTeachingTaskService.getStuIdSetByTeachingTaskId(teachingTaskIdSet);
        ValidateHandler.checkParameter(!stuIdSet.contains(stuId), TeacherErrorCodeEnum.STUDENT_ILLEGAL);
        return teacherEntity;
    }

    /**
     * 校验教师是否有操作通告的权限
     *
     * @param username 教师工号
     * @param noticeId 通告id
     */
    public TeachingTaskNoticeEntity checkNoticeId(Long noticeId, String username) {
        //获取原通告对象并校验
        TeachingTaskNoticeEntity teachingTaskNoticeEntity = teachingTaskNoticeService.getById(noticeId);
        ValidateHandler.checkParameter(teachingTaskNoticeEntity == null, TeacherErrorCodeEnum.NOTICE_NOT_EXISTS);
        //权限校验
        checkTeachingTask(username, teachingTaskNoticeEntity.getTeachingTaskId());
        return teachingTaskNoticeEntity;
    }
}
