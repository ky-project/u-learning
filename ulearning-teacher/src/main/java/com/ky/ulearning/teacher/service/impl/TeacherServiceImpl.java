package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.teacher.dao.TeacherDao;
import com.ky.ulearning.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luyuhao
 * @since 20/01/26 21:45
 */
@Service
@CacheConfig(cacheNames = "teacher")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class TeacherServiceImpl extends BaseService implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public TeacherEntity getByTeaNumber(String teaNumber) {
        return teacherDao.getByTeaNumber(teaNumber);
    }
}
