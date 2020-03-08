package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;

/**
 * 学生测试service - 接口
 *
 * @author luyuhao
 * @since 20/03/08 00:51
 */
public interface StudentExaminationTaskService {

    /**
     * 添加学生测试
     *
     * @param studentExaminationTaskDto 学生测试对象
     */
    void add(StudentExaminationTaskDto studentExaminationTaskDto);
}
