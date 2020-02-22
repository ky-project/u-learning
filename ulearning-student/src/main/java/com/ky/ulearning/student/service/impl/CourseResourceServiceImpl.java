package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.student.dao.CourseFileDao;
import com.ky.ulearning.student.dao.CourseResourceDao;
import com.ky.ulearning.student.service.CourseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luyuhao
 * @since 20/02/22 20:40
 */
@Service
@CacheConfig(cacheNames = "courseResource")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseResourceServiceImpl extends BaseService implements CourseResourceService {

    @Autowired
    private CourseResourceDao courseResourceDao;

    @Autowired
    private CourseFileDao courseFileDao;
}
