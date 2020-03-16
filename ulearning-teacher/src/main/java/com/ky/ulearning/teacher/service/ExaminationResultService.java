package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.common.vo.QuantityVo;
import com.ky.ulearning.spi.teacher.vo.CourseQuestionDetailVo;

import java.util.List;
import java.util.Map;

/**
 * 测试结果service - 接口
 *
 * @author luyuhao
 * @since 2020/03/11 01:13
 */
public interface ExaminationResultService {

    /**
     * 根据学生测试id查询所有课程试题
     *
     * @param examiningId    学生测试id
     * @param quantityVoList 题目信息voList
     * @return 试题分类map
     */
    Map<Integer, List<CourseQuestionDetailVo>> getCourseQuestionVoByExaminingId(Long examiningId, List<QuantityVo> quantityVoList);
}
