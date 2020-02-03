package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.teacher.dto.CourseQuestionDto;
import com.ky.ulearning.spi.teacher.entity.CourseQuestionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
     * @param courseQuestionDto 待插入的试题对象
     */
    void insert(CourseQuestionDto courseQuestionDto);

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
}