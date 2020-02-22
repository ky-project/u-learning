package com.ky.ulearning.student.common.utils;

import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.teacher.entity.StudentTeachingTaskEntity;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.service.StudentTeachingTaskService;
import com.ky.ulearning.student.service.TeachingTaskNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 学生选课相关工具类
 *
 * @author luyuhao
 * @since 20/02/22 14:38
 */
@Component
public class StudentTeachingTaskUtil {

    @Autowired
    private StudentTeachingTaskService studentTeachingTaskService;

    @Autowired
    private TeachingTaskNoticeService teachingTaskNoticeService;

    /**
     * 验证学生是否已经选修该课程
     */
    public boolean selectedTeachingTask(Long teachingTaskId, Long stuId) {
        StudentTeachingTaskEntity studentTeachingTaskEntity = studentTeachingTaskService.getByTeachingIdAndStuId(teachingTaskId, stuId);
        return studentTeachingTaskEntity != null;
    }

    /**
     * 验证学生是否有操作通告的权限
     */
    public TeachingTaskNoticeEntity checkNoticeId(Long noticeId, Long stuId) {
        Set<Long> teachingTaskIdSet = studentTeachingTaskService.getTeachingTaskIdSetByStuId(stuId);
        Set<Long> noticeIdSet = teachingTaskNoticeService.getIdSetByTeachingTaskIdSet(teachingTaskIdSet);
        //是否包含该通告id
        ValidateHandler.checkParameter(! noticeIdSet.contains(noticeId), StudentErrorCodeEnum.TEACHING_TASK_NOTICE_ILLEGAL);
        TeachingTaskNoticeEntity teachingTaskNoticeEntity = teachingTaskNoticeService.getById(noticeId);
        ValidateHandler.checkNull(teachingTaskNoticeEntity, StudentErrorCodeEnum.NOTICE_NOT_EXISTS);
        return teachingTaskNoticeEntity;
    }
}
