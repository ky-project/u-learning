package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.teacher.dto.CourseQuestionDto;
import com.ky.ulearning.spi.teacher.dto.QuestionDto;

import java.util.List;

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
     * @param questionDto 待保存的试题对象
     */
    void save(QuestionDto questionDto);

    /**
     * 分页查询课程试题列表
     *
     * @param pageParam         分页参数
     * @param courseQuestionDto 筛选条件
     * @return 封装课程试题的分页对象
     */
    PageBean<CourseQuestionDto> pageList(PageParam pageParam, CourseQuestionDto courseQuestionDto);

    /**
     * 根据id查询课程试题
     *
     * @param id 试题id
     * @return 课程试题对象
     */
    CourseQuestionDto getById(Long id);

    /**
     * 更新试题
     *
     * @param questionDto 待更新的试题对象
     */
    void update(QuestionDto questionDto);

    /**
     * 删除试题
     *
     * @param id       试题id
     * @param updateBy 更新者
     */
    void delete(Long id, String updateBy);

    /**
     * 查询所有知识模块
     *
     * @return 知识模块-知识模块
     */
    List<KeyLabelVo> getAllKnowledge();

    /**
     * 根据课程id查询知识模块
     *
     * @param courseId 课程id
     * @return 知识模块-知识模块
     */
    List<KeyLabelVo> getKnowledgeByCourseId(Long courseId);
}
