package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.student.dao.CourseDocumentationDao;
import com.ky.ulearning.student.dao.CourseFileDao;
import com.ky.ulearning.student.dao.TeachingTaskDao;
import com.ky.ulearning.student.service.CourseDocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/22 20:35
 */
@Service
@CacheConfig(cacheNames = "courseDocumentation")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseDocumentationServiceImpl extends BaseService implements CourseDocumentationService {

    @Autowired
    private CourseDocumentationDao courseDocumentationDao;

    @Autowired
    private CourseFileDao courseFileDao;

    @Autowired
    private TeachingTaskDao teachingTaskDao;

    @Override
    public CourseFileDocumentationDto getByTeachingTaskId(Long teachingTaskId) {
        TeachingTaskDto teachingTaskDto = teachingTaskDao.getDtoById(teachingTaskId);
        String teachingTaskRootFolderName = "[" + teachingTaskDto.getTerm() + "]" + teachingTaskDto.getTeachingTaskAlias() + "#" + teachingTaskId;
        //查询课程文件根路径
        CourseFileEntity courseFileEntity = courseFileDao.getByCourseIdAndFileName(teachingTaskDto.getCourseId(), MicroConstant.ROOT_FOLDER);
        if (StringUtil.isEmpty(courseFileEntity)) {
            return new CourseFileDocumentationDto();
        }
        //查询教师根目录
        CourseFileDocumentationDto teacherFileRootFolder = courseDocumentationDao.getByCourseIdAndFileName(teachingTaskDto.getCourseId(), teachingTaskDto.getTeaNumber());
        if (StringUtil.isEmpty(teacherFileRootFolder)) {
            return new CourseFileDocumentationDto();
        }
        //查询教学任务根目录
        CourseFileDocumentationDto teachingTaskFileRootFolder = courseDocumentationDao.getByCourseIdAndFileName(teachingTaskDto.getCourseId(), teachingTaskRootFolderName);
        if (StringUtil.isEmpty(teachingTaskFileRootFolder)) {
            return new CourseFileDocumentationDto();
        }
        return teachingTaskFileRootFolder;
    }

    @Override
    public List<CourseFileDocumentationDto> getList(CourseFileDocumentationDto courseFileDocumentationDto) {
        return courseDocumentationDao.getList(courseFileDocumentationDto);
    }

    @Override
    public CourseFileDocumentationDto getById(Long id) {
        return courseDocumentationDao.getById(id);
    }
}
