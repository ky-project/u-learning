package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.teacher.dao.CourseQuestionDao;
import com.ky.ulearning.teacher.service.CourseQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 试题service - 实现类
 *
 * @author luyuhao
 * @since 20/02/03 19:53
 */
@Service
@CacheConfig(cacheNames = {"course", "question"})
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseQuestionServiceImpl extends BaseService implements CourseQuestionService {

    @Autowired
    private CourseQuestionDao courseQuestionDao;
}
