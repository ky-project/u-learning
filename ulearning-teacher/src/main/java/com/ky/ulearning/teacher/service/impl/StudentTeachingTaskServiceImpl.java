package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.teacher.dao.StudentTeachingTaskDao;
import com.ky.ulearning.teacher.service.StudentTeachingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生选课service - 接口类
 *
 * @author luyuhao
 * @since 20/01/30 00:31
 */
@Service
@CacheConfig(cacheNames = {"student", "course", "teacher"})
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentTeachingTaskServiceImpl extends BaseService implements StudentTeachingTaskService {

    @Autowired
    private StudentTeachingTaskDao studentTeachingTaskDao;
}
