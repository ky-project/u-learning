package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.student.vo.CourseQuestionVo;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;

import java.util.List;

/**
 * 课程试题service - 接口
 *
 * @author luyuhao
 * @since 2020/03/08 21:42
 */
public interface CourseQuestionService {

    /**
     * 根据知识点和试题难度查询所有试题
     *
     * @param questionKnowledges 知识点集合
     * @param questionDifficulty 难点
     * @param courseId 课程id
     * @return 试题集合
     */
    List<CourseQuestionVo> getByKnowledgeAndDifficultyAndCourseId(List<KeyLabelVo> questionKnowledges, Integer questionDifficulty, Long courseId);
}
