package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.CourseQuestionDto;
import com.ky.ulearning.spi.teacher.dto.QuestionDto;
import com.ky.ulearning.spi.teacher.entity.CourseQuestionEntity;
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
     * 插入试题记录
     *
     * @param questionDto 待插入的试题对象
     */
    void insert(QuestionDto questionDto);

    /**
     * 根据id查询试题
     *
     * @param id 试题id
     * @return 试题对象
     */
    CourseQuestionEntity getById(Long id);

    /**
     * 更新试题
     *
     * @param courseQuestionDto 待更新的试题对象
     */
    void update(CourseQuestionDto courseQuestionDto);

    /**
     * 分页查询课程试题
     *
     * @param courseQuestionDto 筛选条件
     * @param pageParam         分页参数
     * @return 课程试题集合
     */
    List<CourseQuestionDto> listPage(@Param("courseQuestionDto") CourseQuestionDto courseQuestionDto,
                                     @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询课程试题 - 总记录数
     *
     * @param courseQuestionDto 筛选条件
     * @return 总记录数
     */
    Integer countListPage(@Param("courseQuestionDto") CourseQuestionDto courseQuestionDto);
}