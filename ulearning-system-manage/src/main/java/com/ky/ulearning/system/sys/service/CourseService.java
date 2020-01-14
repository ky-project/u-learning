package com.ky.ulearning.system.sys.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.CourseDto;
import com.ky.ulearning.spi.system.entity.CourseEntity;

/**
 * 课程service - 接口类
 *
 * @author luyuhao
 * @since 20/01/13 23:49
 */
public interface CourseService {

    /**
     * 分页查询课程列表
     *
     * @param courseDto 筛选条件
     * @param pageParam 分页参数
     * @return 封装课程的分页对象
     */
    PageBean<CourseEntity> pageCourseList(CourseDto courseDto, PageParam pageParam);

    /**
     * 添加课程信息
     *
     * @param courseDto 课程信息
     */
    void insert(CourseDto courseDto);

    /**
     * 根据id查询课程信息
     *
     * @param id 课程id
     * @return 课程信息
     */
    CourseEntity getById(Long id);

    /**
     * 更新课程信息
     *
     * @param courseDto 待更新的课程信息
     */
    void update(CourseDto courseDto);

    /**
     * 删除课程
     *
     * @param id        课程id
     * @param updaterBy 更新者
     */
    void delete(Long id, String updaterBy);
}
