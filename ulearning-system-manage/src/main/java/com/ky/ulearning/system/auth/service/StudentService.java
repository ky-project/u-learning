package com.ky.ulearning.system.auth.service;

import com.ky.ulearning.spi.system.dto.StudentDto;

/**
 * 学生service - 接口类
 *
 * @author luyuhao
 * @since 20/01/18 22:52
 */
public interface StudentService {

    /**
     * 插入学生信息
     *
     * @param studentDto 学生对象
     */
    void save(StudentDto studentDto);
}
