package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.teacher.dto.CourseDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.teacher.dao.CourseDocumentationDao;
import com.ky.ulearning.teacher.dao.CourseFileDao;
import com.ky.ulearning.teacher.service.CourseDocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文件资料service - 接口类
 *
 * @author luyuhao
 * @since 20/02/14 20:32
 */
@Service
@CacheConfig(cacheNames = "courseDocumentation")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseDocumentationServiceImpl extends BaseService implements CourseDocumentationService {

    @Autowired
    private CourseDocumentationDao courseDocumentationDao;

    @Autowired
    private CourseFileDao courseFileDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(CourseDocumentationDto courseDocumentationDto, CourseFileDto courseFileDto) {
        courseFileDao.insert(courseFileDto);
        //设置文件资料所属课程文件id
        courseDocumentationDto.setFileId(courseFileDto.getId());
        //插入文件资料对象
        courseDocumentationDao.insert(courseDocumentationDto);
    }

    @Override
    public List<CourseFileDocumentationDto> getList(CourseFileDocumentationDto courseFileDocumentationDto) {
        return courseDocumentationDao.getList(courseFileDocumentationDto);
    }

    @Override
    public CourseFileDocumentationDto getByFileId(Long fileId) {
        return courseDocumentationDao.getByFileId(fileId);
    }

    @Override
    public CourseFileDocumentationDto getById(Long id) {
        return courseDocumentationDao.getById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void update(CourseFileDocumentationDto courseFileDocumentationDto) {
        //更新文件资料信息
        if(StringUtil.isNotEmpty(courseFileDocumentationDto.getId())){
            CourseDocumentationDto courseDocumentationDto = new CourseDocumentationDto();
            courseDocumentationDto.setId(courseFileDocumentationDto.getId());
            courseDocumentationDto.setUpdateBy(courseFileDocumentationDto.getUpdateBy());
            courseDocumentationDto.setDocumentationTitle(courseFileDocumentationDto.getDocumentationTitle());
            courseDocumentationDto.setDocumentationSummary(courseFileDocumentationDto.getDocumentationSummary());
            courseDocumentationDto.setDocumentationCategory(courseFileDocumentationDto.getDocumentationCategory());
            courseDocumentationDto.setDocumentationShared(courseFileDocumentationDto.getDocumentationShared());
            courseDocumentationDao.update(courseDocumentationDto);
        }
        //更新课程文件信息
        if(StringUtil.isNotEmpty(courseFileDocumentationDto.getFileId())){
            CourseFileDto courseFileDto = new CourseFileDto();
            courseFileDto.setUpdateBy(courseFileDocumentationDto.getUpdateBy());
            courseFileDto.setId(courseFileDocumentationDto.getFileId());
            courseFileDto.setFileName(courseFileDocumentationDto.getFileName());
            courseFileDao.update(courseFileDto);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, Long fileId, String updateBy) {
        courseDocumentationDao.updateValidById(id, updateBy, 0);
        courseFileDao.updateValidById(fileId, updateBy, 0);
    }
}
