package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.vo.CourseQuestionVo;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.student.dao.CourseQuestionDao;
import com.ky.ulearning.student.service.CourseQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author luyuhao
 * @since 2020/03/08 21:43
 */
@Service
@CacheConfig(cacheNames = "courseQuestion")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseQuestionServiceImpl extends BaseService implements CourseQuestionService {

    @Autowired
    private CourseQuestionDao courseQuestionDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<CourseQuestionVo> getByKnowledgeAndDifficultyAndCourseId(List<KeyLabelVo> questionKnowledges, Integer questionDifficulty, Long courseId) {
        return Optional.ofNullable(courseQuestionDao.getByKnowledgeAndDifficulty(questionKnowledges, questionDifficulty, courseId))
                .orElse(Collections.emptyList());
    }
}
