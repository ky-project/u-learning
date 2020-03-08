package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.student.entity.StudentExaminationTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 学生测试dao
 *
 * @author luyuhao
 * @since 2020/03/08 00:26
 */
@Mapper
@Repository
public interface StudentExaminationTaskDao {

    /**
     * 插入记录
     *
     * @param studentExaminationTaskDto 待插入的学生测试对象
     */
    void insert(StudentExaminationTaskDto studentExaminationTaskDto);

    /**
     * 根据id查询学生测试信息
     *
     * @param id 学生测试id
     * @return 学生测试对象
     */
    StudentExaminationTaskEntity getById(Long id);

    /**
     * 更新学生测试信息
     *
     * @param studentExaminationTaskDto 待更新的学生测试对象
     */
    void update(StudentExaminationTaskDto studentExaminationTaskDto);

}
