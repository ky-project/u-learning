package com.ky.ulearning.student.common.utils;

import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.spi.teacher.entity.StudentTeachingTaskEntity;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.service.*;
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

    @Autowired
    private TeachingTaskExperimentService teachingTaskExperimentService;

    @Autowired
    private ExperimentResultService experimentResultService;

    @Autowired
    private ExaminationTaskService examinationTaskService;

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

    /**
     * 验证学生是否有操作文件的权限
     */
    public CourseFileEntity checkCourseFileIdByStuId(Long fileId, Long stuId) {
        CourseFileEntity courseFileEntity = courseFileService.getById(fileId);
        ValidateHandler.checkNull(courseFileEntity, StudentErrorCodeEnum.COURSE_FILE_NOT_EXISTS);
        //根据学生id查询该学生选的所有教学任务所对应的courseId
        Set<Long> courseIdSet = studentTeachingTaskService.getCourseIdSetByStuId(stuId);
        ValidateHandler.checkParameter(!courseIdSet.contains(courseFileEntity.getCourseId()), StudentErrorCodeEnum.COURSE_FILE_ILLEGAL);
        return courseFileEntity;
    }

    /**
     * 验证学生是否有操作实验的权限
     */
    public TeachingTaskExperimentDto checkExperimentId(Long experimentId, Long stuId) {
        TeachingTaskExperimentDto teachingTaskExperimentDto = teachingTaskExperimentService.getById(experimentId);
        //校验
        ValidateHandler.checkParameter(teachingTaskExperimentDto == null, StudentErrorCodeEnum.EXPERIMENT_NOT_EXISTS);
        selectedTeachingTask(teachingTaskExperimentDto.getTeachingTaskId(), stuId);
        return teachingTaskExperimentDto;
    }

    /**
     * 验证学生是否有操作实验结果的权限
     */
    public ExperimentResultEntity checkExperimentResultId(Long experimentResultId, Long stuId) {
        ExperimentResultEntity experimentResultEntity = experimentResultService.getById(experimentResultId);
        ValidateHandler.checkNull(experimentResultEntity, StudentErrorCodeEnum.EXPERIMENT_RESULT_NOT_EXISTS);
        checkExperimentId(experimentResultEntity.getExperimentId(), stuId);
        return experimentResultEntity;
    }

    /**
     * 验证学生是否有操作测试的权限
     */
    public ExaminationTaskEntity checkExaminationId(Long examinationId, Long stuId) {
        ExaminationTaskEntity examinationTaskEntity = examinationTaskService.getById(examinationId);
        //校验
        ValidateHandler.checkParameter(examinationTaskEntity == null, StudentErrorCodeEnum.EXAMINATION_NOT_EXISTS);
        selectedTeachingTask(examinationTaskEntity.getTeachingTaskId(), stuId);
        return examinationTaskEntity;
    }
}
