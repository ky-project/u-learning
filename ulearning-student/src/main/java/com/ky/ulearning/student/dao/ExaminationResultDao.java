package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.common.vo.CourseQuestionVo;
import com.ky.ulearning.spi.student.dto.ExaminationResultDto;
import com.ky.ulearning.spi.student.entity.ExaminationResultEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 测试结果dao
 *
 * @author luyuhao
 * @since 2020/03/11 01:09
 */
@Mapper
@Repository
public interface ExaminationResultDao {

    /**
     * 插入测试结果记录
     *
     * @param examinationResultDto 测试结果对象
     */
    void insert(ExaminationResultDto examinationResultDto);

    /**
     * 根据id查询测试结果
     *
     * @param id id
     * @return 测试结果对象
     */
    ExaminationResultEntity getById(Long id);

    /**
     * 更新测试结果
     *
     * @param examinationResultDto 待更新的测试结果
     */
    void update(ExaminationResultDto examinationResultDto);

    /**
     * 批量新增测试结果
     *
     * @param examinationResultDtoList 测试结果集合
     */
    void batchInsert(@Param("examinationResultDtoList") List<ExaminationResultDto> examinationResultDtoList);

    /**
     * 根据学生测试id查询所有课程试题
     *
     * @param examiningId 学生测试id
     * @return 试题分类map
     */
    List<CourseQuestionVo> getCourseQuestionVoByExaminingId(Long examiningId);
}
