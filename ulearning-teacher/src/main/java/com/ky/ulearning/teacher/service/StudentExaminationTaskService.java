package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;

/**
 * 学生测试service - 接口
 *
 * @author luyuhao
 * @since 2020/03/16 00:47
 */
public interface StudentExaminationTaskService {

    /**
     * 分页查询学生测试
     *
     * @param pageParam                 分页参数
     * @param studentExaminationTaskDto 筛选条件
     * @return 学生测试信息
     */
    PageBean<StudentExaminationTaskDto> pageList(PageParam pageParam, StudentExaminationTaskDto studentExaminationTaskDto);

    /**
     * 根据id查询学生测试信息
     *
     * @param id id
     * @return 学生测试信息
     */
    StudentExaminationTaskDto getById(Long id);
}
