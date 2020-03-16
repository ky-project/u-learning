package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.teacher.vo.CourseQuestionDetailVo;
import org.apache.ibatis.annotations.Mapper;
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
     * 根据学生测试id查询所有课程试题
     *
     * @param examiningId 学生测试id
     * @return 试题分类map
     */
    List<CourseQuestionDetailVo> getCourseQuestionDetailVoByExaminingId(Long examiningId);
}
