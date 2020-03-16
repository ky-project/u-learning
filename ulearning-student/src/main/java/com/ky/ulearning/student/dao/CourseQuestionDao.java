package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.student.vo.CourseQuestionVo;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 试题dao
 *
 * @author luyuhao
 * @since 2020/02/03 19:44
 */
@Mapper
@Repository
public interface CourseQuestionDao {

    /**
     * 根据知识点和试题难度查询所有试题
     *
     * @param questionKnowledges 知识点集合
     * @param questionDifficulty 难点
     * @param courseId           课程id
     * @return 试题集合
     */
    List<CourseQuestionVo> getByKnowledgeAndDifficulty(@Param("questionKnowledges") List<KeyLabelVo> questionKnowledges,
                                                       @Param("questionDifficulty") Integer questionDifficulty,
                                                       @Param("courseId") Long courseId);
}
