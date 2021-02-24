package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.student.dto.ExaminationResultDto;
import com.ky.ulearning.spi.teacher.vo.CourseQuestionDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
     * 根据学生测试id查询所有课程试题
     *
     * @param examiningId 学生测试id
     * @return 试题分类map
     */
    List<CourseQuestionDetailVo> getCourseQuestionDetailVoByExaminingId(Long examiningId);

    /**
     * 根据学生测试id查询所有测试结果
     *
     * @param examiningId 学生测试id
     * @return 测试结果集合
     */
    List<ExaminationResultDto> getByExaminingId(Long examiningId);

    /**
     * 根据学生测试id查询学生成绩和准确率
     *
     * @param examiningId 学生测试id
     * @return stuScore、accuracy
     */
    Map<String, Object> getScoreAndAccuracyByExaminingId(Long examiningId);

    /**
     * 删除测试结果
     *
     * @param examiningId 学生测试id
     * @param username    用户账号
     * @author luyuhao
     * @date 2021/02/25 00:56
     */
    void deleteByExaminingId(@Param("examiningId") Long examiningId, @Param("username") String username);
}
