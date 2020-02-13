package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.teacher.dao.CourseFileDao;
import com.ky.ulearning.teacher.service.CourseFileEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 课程文件service - 实现类
 *
 * @author luyuhao
 * @since 20/02/14 02:56
 */
@Service
@CacheConfig(cacheNames = "courseFile")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseFileEntityServiceImpl extends BaseService implements CourseFileEntityService {

    @Autowired
    private CourseFileDao courseFileDao;
}
