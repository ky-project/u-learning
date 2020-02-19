package com.ky.ulearning.teacher.common.utils;

import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import com.ky.ulearning.spi.teacher.dto.CourseQuestionDto;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
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

    @Autowired
    private CourseQuestionService courseQuestionService;

    @Autowired
    private TeachingTaskExperimentService teachingTaskExperimentService;

    @Autowired
    private ExaminationTaskService examinationTaskService;

    @Autowired
    private CourseFileService courseFileService;

    @Autowired
    private CourseDocumentationService courseDocumentationService;

    @Autowired
    private CourseResourceService courseResourceService;

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
        Set<Long> teachingTaskIdSet = Optional.ofNullable(teachingTaskService.getIdByTeaId(teacherEntity.getId()))
                .orElse(Collections.emptySet());
        ValidateHandler.checkParameter(!teachingTaskIdSet.contains(teachingTaskId), TeacherErrorCodeEnum.TEACHING_TASK_ID_ILLEGAL);
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
        Set<Long> teachingTaskIdSet = Optional.ofNullable(teachingTaskService.getIdByTeaId(teacherEntity.getId()))
                .orElse(Collections.emptySet());
        Set<Long> stuIdSet = Optional.ofNullable(studentTeachingTaskService.getStuIdSetByTeachingTaskId(teachingTaskIdSet))
                .orElse(Collections.emptySet());
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

    /**
     * 校验教师是否有操作课程的权限
     *
     * @param courseId 课程id
     * @param username 教师工号
     */
    public void checkCourseId(Long courseId, String username) {
        TeacherEntity teacherEntity = teacherService.getByTeaNumber(username);
        //教师number是否存在
        ValidateHandler.checkParameter(teacherEntity == null, TeacherErrorCodeEnum.TEA_NUMBER_NOT_EXISTS);
        //查询可操作的课程id
        Set<Long> courseIdSet = Optional.ofNullable(teachingTaskService.getCourseIdByTeaId(teacherEntity.getId()))
                .orElse(Collections.emptySet());
        ValidateHandler.checkParameter(!courseIdSet.contains(courseId), TeacherErrorCodeEnum.COURSE_ILLEGAL);
    }

    /**
     * 校验教师是否有操作课程试题的权限
     *
     * @param username   教师工号
     * @param questionId 课程试题id
     */
    public CourseQuestionDto checkCourseQuestionId(Long questionId, String username) {
        CourseQuestionDto courseQuestionDto = courseQuestionService.getById(questionId);
        //校验
        ValidateHandler.checkParameter(courseQuestionDto == null, TeacherErrorCodeEnum.COURSE_QUESTION_NOT_EXISTS);
        checkCourseId(courseQuestionDto.getCourseId(), username);
        return courseQuestionDto;
    }

    /**
     * 校验教师是否有操作实验的权限
     *
     * @param experimentId 实验id
     * @param username     教师工号
     */
    public TeachingTaskExperimentDto checkExperimentId(Long experimentId, String username) {
        TeachingTaskExperimentDto teachingTaskExperimentDto = teachingTaskExperimentService.getById(experimentId);
        //校验
        ValidateHandler.checkParameter(teachingTaskExperimentDto == null, TeacherErrorCodeEnum.EXPERIMENT_NOT_EXISTS);
        checkTeachingTask(username, teachingTaskExperimentDto.getTeachingTaskId());
        return teachingTaskExperimentDto;
    }

    /**
     * 校验教师是否有操作测试任务的权限
     *
     * @param examinationId 测试任务id
     * @param username      教师工号
     */
    public ExaminationTaskEntity checkExaminationId(Long examinationId, String username) {
        ExaminationTaskEntity examinationTaskEntity = examinationTaskService.getById(examinationId);
        //校验
        ValidateHandler.checkParameter(examinationTaskEntity == null, TeacherErrorCodeEnum.EXAMINATION_NOT_EXISTS);
        checkTeachingTask(username, examinationTaskEntity.getTeachingTaskId());
        return examinationTaskEntity;
    }

    /**
     * 校验教师是否有操作课程文件的权限
     *
     * @param courseFileId 课程文件id
     * @param username     教师工号
     * @return 课程文件对象
     */
    public CourseFileEntity checkCourseFileId(Long courseFileId, String username) {
        CourseFileEntity courseFileEntity = courseFileService.getById(courseFileId);
        ValidateHandler.checkNull(courseFileEntity, TeacherErrorCodeEnum.COURSE_FILE_NOT_EXISTS);
        //校验课程id
        checkCourseId(courseFileEntity.getCourseId(), username);
        return courseFileEntity;
    }

    /**
     * 校验教师是否有操作文件资料的权限
     *
     * @param documentationId 文件资料id
     * @param username        教师工号
     */
    public CourseFileDocumentationDto checkDocumentationId(Long documentationId, String username) {
        CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationService.getById(documentationId);
        //空值校验
        ValidateHandler.checkNull(courseFileDocumentationDto, TeacherErrorCodeEnum.DOCUMENTATION_NOT_EXISTS);
        //校验课程文件id
        checkCourseFileId(courseFileDocumentationDto.getFileId(), username);
        return courseFileDocumentationDto;
    }

    /**
     * 校验教师是否有操作教学资源的权限
     *
     * @param resourceId 教学资源id
     * @param username   教师工号
     */
    public CourseFileResourceDto checkResourceId(Long resourceId, String username) {
        CourseFileResourceDto courseFileResourceDto = courseResourceService.getById(resourceId);
        //控制检验
        ValidateHandler.checkNull(courseFileResourceDto, TeacherErrorCodeEnum.RESOURCE_NOT_EXISTS);
        //校验课程文件id
        checkCourseFileId(courseFileResourceDto.getFileId(), username);
        return courseFileResourceDto;
    }

    /**
     * 校验teachingTaskId对应的courseId是否与courseId一致
     *
     * @param teachingTaskId 教学任务id
     * @param courseId       课程id
     */
    public void checkTeachingTaskIdAndCourseId(Long teachingTaskId, Long courseId) {
        Long courseIdCheck = teachingTaskService.getCourseIdById(teachingTaskId);
        ValidateHandler.checkNull(courseIdCheck, TeacherErrorCodeEnum.COURSE_ID_NOT_EXISTS);
        ValidateHandler.checkParameter(!courseIdCheck.equals(courseId), TeacherErrorCodeEnum.TEACHING_TASK_ID_ERROR);
    }
}
