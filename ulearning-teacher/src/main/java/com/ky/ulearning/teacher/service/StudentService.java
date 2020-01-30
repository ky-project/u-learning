package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.system.entity.StudentEntity;

/**
 * 学生service - 接口类
 *
 * @author luyuhao
 * @since 20/01/30 20:42
 */
public interface StudentService {
    /**
     * 根据id获取学生信息
     *
     * @param id 学生id
     * @return 学生对象
     */
    StudentEntity getById(Long id);
}
