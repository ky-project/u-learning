package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;

/**
 * @author luyuhao
 * @since 20/03/03 00:37
 */
public interface CourseFileService {

    /**
     * 根据id查询课程文件信息
     *
     * @param id 课程文件id
     * @return 课程文件对象
     */
    CourseFileEntity getById(Long id);
}
