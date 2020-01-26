package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.system.entity.CourseEntity;
import com.ky.ulearning.spi.system.vo.CourseVo;
import com.ky.ulearning.teacher.dao.CourseDao;
import com.ky.ulearning.teacher.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程service
 *
 * @author luyuhao
 * @since 20/01/26 21:31
 */
@Service
@CacheConfig(cacheNames = "course")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseServiceImpl extends BaseService implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<CourseVo> getAll() {
        return courseDao.getAllVo();
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public CourseEntity getById(Long id) {
        return courseDao.getById(id);
    }
}
