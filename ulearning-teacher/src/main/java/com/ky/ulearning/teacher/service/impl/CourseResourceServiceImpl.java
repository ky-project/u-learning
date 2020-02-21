package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import com.ky.ulearning.spi.teacher.dto.CourseResourceDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.teacher.common.utils.CourseFileUtil;
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
        CourseResourceDto courseResourceDto = new CourseResourceDto();
        courseResourceDto.setId(courseFileResourceDto.getId());
        courseResourceDto.setUpdateBy(courseFileResourceDto.getUpdateBy());
        courseResourceDto.setResourceTitle(courseFileResourceDto.getResourceTitle());
        courseResourceDto.setResourceSummary(courseFileResourceDto.getResourceSummary());
        courseResourceDto.setResourceType(courseFileResourceDto.getResourceType());
        courseResourceDto.setResourceShared(courseFileResourceDto.getResourceShared());
        courseResourceDao.update(courseResourceDto);
        //更新课程文件信息
        CourseFileDto courseFileDto = new CourseFileDto();
        courseFileDto.setUpdateBy(courseFileResourceDto.getUpdateBy());
        courseFileDto.setId(courseFileResourceDto.getFileId());
        courseFileDto.setFileName(courseFileResourceDto.getFileName());
        courseFileDao.update(courseFileDto);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, Long fileId, String updateBy) {
        courseResourceDao.updateValidById(id, updateBy, 0);
        courseFileDao.updateValidById(fileId, updateBy, 0);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public CourseFileResourceDto getByCourseIdAndUsername(Long courseId, String username) {
        //获取课程根节点
        CourseFileEntity courseFileEntity = courseFileDao.getByCourseIdAndFileName(courseId, MicroConstant.ROOT_FOLDER);
        //若课程根目录不存在，初始化
        if (StringUtil.isEmpty(courseFileEntity)) {
            //创建课程根文件夹
            CourseFileDto rootCourseFileDto = createFolder(courseId, MicroConstant.ROOT_FOLDER, MicroConstant.ROOT_FOLDER_PARENTID);
            courseFileDao.insert(rootCourseFileDto);
            //创建用户根文件夹
            CourseFileDto teacherCourseFileDto = createFolder(courseId, username, rootCourseFileDto.getId());
            courseFileDao.insert(teacherCourseFileDto);
            CourseResourceDto courseResourceDtoFolder = CourseFileUtil.createCourseResourceDtoFolder(username);
            courseResourceDtoFolder.setFileId(teacherCourseFileDto.getId());
            //插入教学资源
            courseResourceDao.insert(courseResourceDtoFolder);
            return courseResourceDao.getById(courseResourceDtoFolder.getId());
        } else {
            //获取教师根节点
            CourseFileEntity teacherCourseFileEntity = courseFileDao.getByParentIdAndFileName(courseFileEntity.getId(), username);
            //如果教师根目录不存，初始化
            if (StringUtil.isEmpty(teacherCourseFileEntity)) {
                CourseFileDto teacherCourseFileDto = createFolder(courseId, username, courseFileEntity.getId());
                courseFileDao.insert(teacherCourseFileDto);
                CourseResourceDto courseResourceDtoFolder = CourseFileUtil.createCourseResourceDtoFolder(username);
                courseResourceDtoFolder.setFileId(teacherCourseFileDto.getId());
                //插入教学资源
                courseResourceDao.insert(courseResourceDtoFolder);
                return courseResourceDao.getById(courseResourceDtoFolder.getId());
            } else {
                CourseFileResourceDto courseFileResourceDto = courseResourceDao.getByFileId(teacherCourseFileEntity.getId());
                //当用户根目录已创建，但教学资源未索引，创建索引
                if(StringUtil.isEmpty(courseFileResourceDto)){
                    CourseResourceDto courseResourceDtoFolder = CourseFileUtil.createCourseResourceDtoFolder(username);
                    courseResourceDtoFolder.setFileId(teacherCourseFileEntity.getId());
                    //插入教学资源
                    courseResourceDao.insert(courseResourceDtoFolder);
                    return courseResourceDao.getById(courseResourceDtoFolder.getId());
                }
                return courseFileResourceDto;
            }
        }
    }

    /**
     * 创建课程文件初始化对象
     */
    private CourseFileDto createFolder(Long courseId, String fileName, Long parentId) {
        CourseFileDto courseFileDto = new CourseFileDto();
        courseFileDto.setCourseId(courseId);
        courseFileDto.setFileName(fileName);
        courseFileDto.setFileType(MicroConstant.FOLDER_TYPE);
        courseFileDto.setFileParentId(parentId);
        courseFileDto.setCreateBy("system");
        courseFileDto.setUpdateBy("system");
        return courseFileDto;
    }

    @Override
    public List<CourseFileResourceDto> getListByFileParentId(Long fileParentId) {
        return courseResourceDao.getListByFileParentId(fileParentId);
    }
}
