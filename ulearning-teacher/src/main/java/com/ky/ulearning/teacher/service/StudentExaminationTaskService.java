package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.excel.StudentExaminationResultExcel;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.spi.teacher.vo.StudentExaminationResultVo;
import com.ky.ulearning.spi.teacher.vo.StudentExaminationStatisticsVo;

import java.util.List;

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

    /**
     * 统计学生测试信息
     *
     * @param examinationTaskEntity 测试任务对象
     * @return 学生测试统计信息
     */
    StudentExaminationStatisticsVo getStudentExaminationStatistics(ExaminationTaskEntity examinationTaskEntity);

    /**
     * 分页查询学生测试结果
     *
     * @param pageParam                  分页参数
     * @param studentExaminationResultVo 查询条件
     * @param examinationParameters      组卷参数
     * @return 学生测试结果分页对象
     */
    PageBean<StudentExaminationResultVo> pageStudentExaminationResultList(PageParam pageParam,
                                                                          StudentExaminationResultVo studentExaminationResultVo,
                                                                          String examinationParameters);

    /**
     * 根据测试任务id查询所有学生测试结果
     *
     * @param examinationTaskId     测试任务id
     * @param examinationParameters 组卷参数
     * @return 学生测试结果集合
     */
    List<StudentExaminationResultExcel> getStudentExaminationResultList(Long examinationTaskId, String examinationParameters);

    /**
     * 重置测试结果
     *
     * @param examiningId 测试id
     * @param username    用户账号
     * @author luyuhao
     * @date 2021/02/25 00:52
     */
    void resetExaminationResult(Long examiningId, String username);
}
