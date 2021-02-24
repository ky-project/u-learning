package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.excel.StudentExaminationResultExcel;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.student.vo.StudentExaminationTaskBaseInfoVo;
import com.ky.ulearning.spi.teacher.vo.ExaminationStatusVo;
import com.ky.ulearning.spi.teacher.vo.StudentExaminationResultVo;
import com.ky.ulearning.spi.teacher.vo.StudentExaminationStatisticsVo;
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

    /**
     * 根据测试任务id统计学生测试基本信息
     *
     * @param examinationTaskId 测试任务id
     * @return 学生测试统计信息
     */
    StudentExaminationStatisticsVo getStudentExaminationStatistics(Long examinationTaskId);

    /**
     * 根据测试任务id查询各个状态的学生数量
     *
     * @param examinationTaskId 测试任务id
     * @return 各个状态的学生数量
     */
    List<ExaminationStatusVo> getExaminationStatus(Long examinationTaskId);

    /**
     * 根据测试任务id查询所有已测试结束的学生id
     *
     * @param examinationTaskId 测试任务id
     * @return id集合
     */
    List<Long> getIdByExaminationTaskId(Long examinationTaskId);

    /**
     * 分页查询学生测试结果
     *
     * @param pageParam                  分页参数
     * @param studentExaminationResultVo 查询条件
     * @return 学生测试结果集合
     */
    List<StudentExaminationResultVo> pageStudentExaminationResultList(@Param("pageParam") PageParam pageParam,
                                                                      @Param("studentExaminationResultVo") StudentExaminationResultVo studentExaminationResultVo);

    /**
     * 分页查询学生测试结果 - 总记录数
     *
     * @param studentExaminationResultVo 查询条件
     * @return 总记录数
     */
    Integer countPageStudentExaminationResultList(@Param("studentExaminationResultVo") StudentExaminationResultVo studentExaminationResultVo);

    /**
     * 根据测试任务id查询学生测试基本信息
     *
     * @param examinationTaskId 测试任务id
     * @return 学生基本信息
     */
    List<StudentExaminationTaskBaseInfoVo> getBaseInfoByExaminationTaskId(Long examinationTaskId);

    /**
     * 根据测试任务id查询学生测试结果
     *
     * @param examinationTaskId 测试任务id
     * @return 学生测试结果
     */
    List<StudentExaminationResultExcel> getStudentExaminationResultList(Long examinationTaskId);

    /**
     * 删除测试记录
     *
     * @param examiningId 测试id
     * @param username    用户账号
     * @author luyuhao
     * @date 2021/02/25 00:54
     */
    void deleteById(@Param("examiningId") Long examiningId, @Param("username") String username);
}
