package com.ky.ulearning.student.common.utils;

import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.spi.teacher.entity.StudentTeachingTaskEntity;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.service.CourseFileService;
import com.ky.ulearning.student.service.StudentTeachingTaskService;
import com.ky.ulearning.student.service.TeachingTaskNoticeService;
import com.ky.ulearning.student.service.TeachingTaskService;
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

    @Autowired
    private CourseFileService courseFileService;

    @Autowired
    private TeachingTaskService teachingTaskService;

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
        ValidateHandler.checkParameter(!noticeIdSet.contains(noticeId), StudentErrorCodeEnum.TEACHING_TASK_NOTICE_ILLEGAL);
        TeachingTaskNoticeEntity teachingTaskNoticeEntity = teachingTaskNoticeService.getById(noticeId);
        ValidateHandler.checkNull(teachingTaskNoticeEntity, StudentErrorCodeEnum.NOTICE_NOT_EXISTS);
        return teachingTaskNoticeEntity;
    }

    /**
     * 验证文件id与教学任务对应的课程id是否一致
     */
    public CourseFileEntity checkCourseFileId(Long fileId, Long teachingTaskId) {
        CourseFileEntity courseFileEntity = courseFileService.getById(fileId);
        ValidateHandler.checkNull(courseFileEntity, StudentErrorCodeEnum.COURSE_FILE_NOT_EXISTS);
        //校验课程id
        checkTeachingTaskIdAndCourseId(teachingTaskId, courseFileEntity.getCourseId());
        return courseFileEntity;
    }

    /**
     * 校验teachingTaskId对应的courseId是否与courseId一致
     *
     * @param teachingTaskId 教学任务id
     * @param courseId       课程id
     */
    public void checkTeachingTaskIdAndCourseId(Long teachingTaskId, Long courseId) {
        Long courseIdCheck = teachingTaskService.getCourseIdById(teachingTaskId);
        ValidateHandler.checkNull(courseIdCheck, StudentErrorCodeEnum.COURSE_ID_NOT_EXISTS);
        ValidateHandler.checkParameter(!courseIdCheck.equals(courseId), StudentErrorCodeEnum.TEACHING_TASK_ID_ERROR);
    }
}
