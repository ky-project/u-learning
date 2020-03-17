package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.student.entity.StudentExaminationTaskEntity;
import com.ky.ulearning.spi.student.vo.StudentExaminationTaskBaseInfoVo;
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

    /**
     * 根据测试任务id和学生id查询学生测试信息
     *
     * @param examinationTaskId 测试任务id
     * @param stuId             学生id
     * @return 学生测试信息
     */
    StudentExaminationTaskEntity getByExaminationTaskIdAndStuId(@Param("examinationTaskId") Long examinationTaskId,
                                                                @Param("stuId") Long stuId);

    /**
     * 根据id查询测试任务参数
     *
     * @param id 学生测试id
     * @return 测试任务参数
     */
    String getExaminationParametersById(Long id);

    /**
     * 根据测试任务id查询学生测试基本信息
     *
     * @param examinationTaskId 测试任务id
     * @return 学生基本信息
     */
    List<StudentExaminationTaskBaseInfoVo> getBaseInfoByExaminationTaskId(Long examinationTaskId);
}
