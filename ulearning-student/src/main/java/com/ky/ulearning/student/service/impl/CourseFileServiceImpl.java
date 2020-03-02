package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.student.dao.CourseFileDao;
import com.ky.ulearning.student.service.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luyuhao
 * @since 20/03/03 00:37
 */
@Service
@CacheConfig(cacheNames = "courseFile")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseFileServiceImpl implements CourseFileService {

    @Autowired
    private CourseFileDao courseFileDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public CourseFileEntity getById(Long id) {
        return courseFileDao.getById(id);
    }
}
