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
}
