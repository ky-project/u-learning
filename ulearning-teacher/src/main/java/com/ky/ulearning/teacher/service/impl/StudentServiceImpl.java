package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.teacher.dao.StudentDao;
import com.ky.ulearning.teacher.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生service - 实现类
 *
 * @author luyuhao
 * @since 20/01/30 20:42
 */
@Service
@CacheConfig(cacheNames = "student")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class StudentServiceImpl extends BaseService implements StudentService {
    @Autowired
    private StudentDao studentDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public StudentEntity getById(Long id) {
        return studentDao.getById(id);
    }
}
