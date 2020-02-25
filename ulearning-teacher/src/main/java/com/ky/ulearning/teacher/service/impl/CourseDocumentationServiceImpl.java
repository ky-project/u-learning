package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.teacher.dto.CourseDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.teacher.common.utils.CourseFileUtil;
import com.ky.ulearning.teacher.dao.CourseDocumentationDao;
import com.ky.ulearning.teacher.dao.CourseFileDao;
import com.ky.ulearning.teacher.service.CourseDocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        CourseDocumentationDto courseDocumentationDto = new CourseDocumentationDto();
        courseDocumentationDto.setId(courseFileDocumentationDto.getId());
        courseDocumentationDto.setUpdateBy(courseFileDocumentationDto.getUpdateBy());
        courseDocumentationDto.setDocumentationTitle(courseFileDocumentationDto.getDocumentationTitle());
        courseDocumentationDto.setDocumentationSummary(courseFileDocumentationDto.getDocumentationSummary());
        courseDocumentationDto.setDocumentationCategory(courseFileDocumentationDto.getDocumentationCategory());
        courseDocumentationDto.setDocumentationShared(courseFileDocumentationDto.getDocumentationShared());
        courseDocumentationDao.update(courseDocumentationDto);
        //更新课程文件信息
        CourseFileDto courseFileDto = new CourseFileDto();
        courseFileDto.setUpdateBy(courseFileDocumentationDto.getUpdateBy());
        courseFileDto.setId(courseFileDocumentationDto.getFileId());
        courseFileDto.setFileName(courseFileDocumentationDto.getFileName());
        courseFileDao.update(courseFileDto);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id, Long fileId, String updateBy) {
        courseDocumentationDao.updateValidById(id, updateBy, 0);
        courseFileDao.updateValidById(fileId, updateBy, 0);
    }

    @Override
    public List<CourseFileDocumentationDto> getListByFileParentId(Long fileParentId) {
        return Optional.ofNullable(courseDocumentationDao.getListByFileParentId(fileParentId))
                .orElse(Collections.emptyList());
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public CourseFileDocumentationDto getByCourseIdAndUsername(Long courseId, String username) {
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
            CourseDocumentationDto courseDocumentationDtoFolder = CourseFileUtil.createCourseDocumentationDtoFolder(username);
            courseDocumentationDtoFolder.setFileId(teacherCourseFileDto.getId());
            //插入文件资料对象
            courseDocumentationDao.insert(courseDocumentationDtoFolder);
            return courseDocumentationDao.getById(courseDocumentationDtoFolder.getId());
        } else {
            //获取教师根节点
            CourseFileEntity teacherCourseFileEntity = courseFileDao.getByParentIdAndFileName(courseFileEntity.getId(), username);
            //如果教师根目录不存，初始化
            if (StringUtil.isEmpty(teacherCourseFileEntity)) {
                CourseFileDto teacherCourseFileDto = createFolder(courseId, username, courseFileEntity.getId());
                courseFileDao.insert(teacherCourseFileDto);
                CourseDocumentationDto courseDocumentationDtoFolder = CourseFileUtil.createCourseDocumentationDtoFolder(username);
                courseDocumentationDtoFolder.setFileId(teacherCourseFileDto.getId());
                //插入文件资料对象
                courseDocumentationDao.insert(courseDocumentationDtoFolder);
                return courseDocumentationDao.getById(courseDocumentationDtoFolder.getId());
            } else {
                CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationDao.getByFileId(teacherCourseFileEntity.getId());
                //当用户根目录已创建，但文件资料未索引，创建索引
                if (StringUtil.isEmpty(courseFileDocumentationDto)) {
                    CourseDocumentationDto courseDocumentationDtoFolder = CourseFileUtil.createCourseDocumentationDtoFolder(username);
                    courseDocumentationDtoFolder.setFileId(teacherCourseFileEntity.getId());
                    //插入文件资料对象
                    courseDocumentationDao.insert(courseDocumentationDtoFolder);
                    return courseDocumentationDao.getById(courseDocumentationDtoFolder.getId());
                }
                return courseFileDocumentationDto;
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
    public List<CourseFileDocumentationDto> getListByFileParentIdAndFileType(Long fileParentId, Integer fileType) {
        return Optional.ofNullable(courseDocumentationDao.getListByFileParentIdAndFileType(fileParentId, fileType))
                .orElse(Collections.emptyList());
    }
}
