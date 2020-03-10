package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.common.vo.CourseQuestionVo;

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
     * 批量添加测试结果
     *
     * @param resMap      试题map
     * @param examiningId 学生测试id
     */
    void batchInsert(Map<Integer, List<CourseQuestionVo>> resMap, Long examiningId);

    /**
     * 根据学生测试id查询所有课程试题
     *
     * @param examiningId 学生测试id
     * @return 试题分类map
     */
    Map<Integer, List<CourseQuestionVo>> getCourseQuestionVoByExaminingId(Long examiningId);
}
