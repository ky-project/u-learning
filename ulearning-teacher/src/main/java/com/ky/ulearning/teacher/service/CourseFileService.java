package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;

/**
 * 课程文件service - 接口类
 *
 * @author luyuhao
 * @since 20/02/14 02:55
 */
public interface CourseFileService {

    /**
     * 根据id查询课程文件信息
     *
     * @param id 课程文件id
     * @return 课程文件对象
     */
    CourseFileEntity getById(Long id);

    /**
     * 根据课程id和工号查询id
     *
     * @param courseId 课程id
     * @param username 工号
     * @return id
     */
    Long getByCourseIdAndUsername(Long courseId, String username);
}
