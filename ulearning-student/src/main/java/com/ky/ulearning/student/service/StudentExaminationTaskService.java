package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.student.entity.StudentExaminationTaskEntity;

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

    /**
     * 根据测试任务id和学生id查询学生测试信息
     *
     * @param examinationTaskId 测试任务id
     * @param stuId             学生id
     * @return 学生测试信息
     */
    StudentExaminationTaskEntity getByExaminationTaskIdAndStuId(Long examinationTaskId, Long stuId);
}
