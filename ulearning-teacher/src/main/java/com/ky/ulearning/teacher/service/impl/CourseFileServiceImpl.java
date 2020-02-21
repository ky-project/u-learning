package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.teacher.dao.CourseFileDao;
import com.ky.ulearning.teacher.service.CourseFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class CourseFileServiceImpl extends BaseService implements CourseFileService {

    @Autowired
    private CourseFileDao courseFileDao;

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public CourseFileEntity getById(Long id) {
        return courseFileDao.getById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(CourseFileDto courseFileDto) {
        courseFileDao.insert(courseFileDto);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, String updateBy) {
        courseFileDao.updateValidById(id, updateBy, 0);
    }
}
