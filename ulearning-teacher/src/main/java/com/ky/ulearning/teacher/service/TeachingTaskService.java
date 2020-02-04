package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;

import java.util.Set;

/**
 * @author luyuhao
 * @since 20/01/26 16:16
 */
public interface TeachingTaskService {
    /**
     * 分页查询课程&教学任务信息
     *
     * @param pageParam             分页参数
     * @param courseTeachingTaskDto 筛选条件
     * @return 返回封装课程&教学任务信息的分页对象
     */
    PageBean<CourseTeachingTaskDto> pageList(PageParam pageParam, CourseTeachingTaskDto courseTeachingTaskDto);

    /**
     * 插入教学任务信息
     *
     * @param teachingTaskDto 教学任务对象
     */
    void insert(TeachingTaskDto teachingTaskDto);

    /**
     * 更新教学任务信息
     *
     * @param teachingTaskDto 教学任务对象
     */
    void update(TeachingTaskDto teachingTaskDto);

    /**
     * 根据教师id查询所有教学任务id集合
     *
     * @param teaId 教师id
     * @return 教学任务id集合
     */
    Set<Long> getIdByTeaId(Long teaId);

    /**
     * 根据教学任务id查询课程教学任务信息
     *
     * @param id 教学任务id
     * @return 返回课程教学任务对象
     */
    CourseTeachingTaskDto getById(Long id);

    /**
     * 根据教师id查询所有课程id集合
     *
     * @param teaId 教师id
     * @return 课程id集合
     */
    Set<Long> getCourseIdByTeaId(Long teaId);
}
