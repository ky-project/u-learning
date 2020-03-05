package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/22 20:39
 */
public interface CourseResourceService {

    /**
     * 根据教学任务id查询课程教学资源信息
     *
     * @param teachingTaskId 教学任务id
     * @return 课程教学资源对象
     */
    CourseFileResourceDto getByTeachingTaskId(Long teachingTaskId);

    /**
     * 查询教学资源集合
     *
     * @param courseFileResourceDto 筛选对象
     * @return 返回课程教学资源集合
     */
    List<CourseFileResourceDto> getList(CourseFileResourceDto courseFileResourceDto);

    /**
     * 根据id查询教学资源信息
     *
     * @param id id
     * @return 教学资源对象
     */
    CourseFileResourceDto getById(Long id);
}
