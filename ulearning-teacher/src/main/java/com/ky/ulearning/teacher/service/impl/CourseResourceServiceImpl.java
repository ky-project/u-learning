package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import com.ky.ulearning.spi.teacher.dto.CourseResourceDto;
import com.ky.ulearning.teacher.dao.CourseFileDao;
import com.ky.ulearning.teacher.dao.CourseResourceDao;
import com.ky.ulearning.teacher.service.CourseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luyuhao
 * @since 20/02/17 19:25
 */
@Service
@CacheConfig(cacheNames = "courseResource")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseResourceServiceImpl extends BaseService implements CourseResourceService {

    @Autowired
    private CourseResourceDao courseResourceDao;

    @Autowired
    private CourseFileDao courseFileDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(CourseResourceDto courseResourceDto, CourseFileDto courseFileDto) {
        courseFileDao.insert(courseFileDto);
        //设置教学资源的课程文件id
        courseResourceDto.setFileId(courseFileDto.getId());
        //插入教学资料对象
        courseResourceDao.insert(courseResourceDto);
    }

    @Override
    public CourseFileResourceDto getByFileId(Long fileId) {
        return courseResourceDao.getByFileId(fileId);
    }
}
