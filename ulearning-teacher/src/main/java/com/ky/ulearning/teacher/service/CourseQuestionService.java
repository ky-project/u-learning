package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.teacher.dto.CourseQuestionDto;

/**
 * 试题service - 接口类
 *
 * @author luyuhao
 * @since 20/02/03 19:52
 */
public interface CourseQuestionService {
    /**
     * 保存试题
     *
     * @param courseQuestionDto 待保存的试题对象
     */
    void save(CourseQuestionDto courseQuestionDto);
}
