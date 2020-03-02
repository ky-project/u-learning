package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;

/**
 * @author luyuhao
 * @since 20/02/22 20:35
 */
public interface CourseDocumentationService {

    /**
     * 根据教学任务id查询课程文件资料信息
     *
     * @param teachingTaskId 教学任务id
     * @return 课程文件资料对象
     */
    CourseFileDocumentationDto getByTeachingTaskId(Long teachingTaskId);
}
