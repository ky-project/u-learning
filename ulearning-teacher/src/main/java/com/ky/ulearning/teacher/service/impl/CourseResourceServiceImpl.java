package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.utils.StringUtil;
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

import java.util.List;

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

    @Override
    public List<CourseFileResourceDto> getList(CourseFileResourceDto courseFileResourceDto) {
        return courseResourceDao.getList(courseFileResourceDto);
    }

    @Override
    public CourseFileResourceDto getById(Long id) {
        return courseResourceDao.getById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(CourseFileResourceDto courseFileResourceDto) {
        //更新教学资源信息
        if (StringUtil.isNotEmpty(courseFileResourceDto.getId())) {
            CourseResourceDto courseResourceDto = new CourseResourceDto();
            courseResourceDto.setId(courseFileResourceDto.getId());
            courseResourceDto.setUpdateBy(courseFileResourceDto.getUpdateBy());
            courseResourceDto.setResourceTitle(courseFileResourceDto.getResourceTitle());
            courseResourceDto.setResourceSummary(courseFileResourceDto.getResourceSummary());
            courseResourceDto.setResourceType(courseFileResourceDto.getResourceType());
            courseResourceDto.setResourceShared(courseFileResourceDto.getResourceShared());
            courseResourceDao.update(courseResourceDto);
        }
        //更新课程文件信息
        if (StringUtil.isNotEmpty(courseFileResourceDto.getFileId())) {
            CourseFileDto courseFileDto = new CourseFileDto();
            courseFileDto.setUpdateBy(courseFileResourceDto.getUpdateBy());
            courseFileDto.setId(courseFileResourceDto.getFileId());
            courseFileDto.setFileName(courseFileResourceDto.getFileName());
            courseFileDao.update(courseFileDto);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, Long fileId, String updateBy) {
        courseResourceDao.updateValidById(id, updateBy, 0);
        courseFileDao.updateValidById(fileId, updateBy, 0);
    }
}
