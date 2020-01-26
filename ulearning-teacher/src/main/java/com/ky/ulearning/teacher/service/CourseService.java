package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.system.entity.CourseEntity;
import com.ky.ulearning.spi.system.vo.CourseVo;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/01/26 21:31
 */
public interface CourseService {

    /**
     * 查询所有课程基本信息
     *
     * @return 课程信息集合
     */
    List<CourseVo> getAll();

    /**
     * 根据id查询课程信息
     *
     * @param id 课程id
     * @return 课程信息
     */
    CourseEntity getById(Long id);
}
