package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * 分页查询学生测试
     *
     * @param pageParam                 分页参数
     * @param studentExaminationTaskDto 筛选条件
     * @return 学生测试信息
     */
    List<StudentExaminationTaskDto> listPage(@Param("studentExaminationTaskDto") StudentExaminationTaskDto studentExaminationTaskDto,
                                             @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询学生测试 - 总记录数
     *
     * @param studentExaminationTaskDto 筛选条件
     * @return 总记录数
     */
    Integer countListPage(@Param("studentExaminationTaskDto") StudentExaminationTaskDto studentExaminationTaskDto);

    /**
     * 根据id查询学生测试信息
     *
     * @param id id
     * @return 学生测试信息
     */
    StudentExaminationTaskDto getById(Long id);
}
