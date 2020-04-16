package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.student.dao.CourseFileDao;
import com.ky.ulearning.student.dao.CourseResourceDao;
import com.ky.ulearning.student.dao.TeachingTaskDao;
import com.ky.ulearning.student.service.CourseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/22 20:40
 */
@Service
@CacheConfig(cacheNames = "courseResource")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class CourseResourceServiceImpl extends BaseService implements CourseResourceService {

    @Autowired
    private CourseResourceDao courseResourceDao;

    @Autowired
    private CourseFileDao courseFileDao;

    @Autowired
    private TeachingTaskDao teachingTaskDao;

    @Override
    public CourseFileResourceDto getByTeachingTaskId(Long teachingTaskId) {
        TeachingTaskDto teachingTaskDto = teachingTaskDao.getDtoById(teachingTaskId);
        String teachingTaskRootFolderName = "[" + teachingTaskDto.getTerm() + "]" + teachingTaskDto.getTeachingTaskAlias() + "#" + teachingTaskId;
        //查询课程文件根路径
        CourseFileEntity courseFileEntity = courseFileDao.getByCourseIdAndFileName(teachingTaskDto.getCourseId(), MicroConstant.ROOT_FOLDER);
        if (StringUtil.isEmpty(courseFileEntity)) {
            return new CourseFileResourceDto();
        }
        //查询教师根目录
        CourseFileResourceDto teacherFileRootFolder = courseResourceDao.getByCourseIdAndFileName(teachingTaskDto.getCourseId(), teachingTaskDto.getTeaNumber());
        if (StringUtil.isEmpty(teacherFileRootFolder)) {
            return new CourseFileResourceDto();
        }
        //查询教学任务根目录
        CourseFileResourceDto teachingTaskFileRootFolder = courseResourceDao.getByCourseIdAndFileName(teachingTaskDto.getCourseId(), teachingTaskRootFolderName);
        if (StringUtil.isEmpty(teachingTaskFileRootFolder)) {
            return new CourseFileResourceDto();
        }
        return teachingTaskFileRootFolder;
    }

    @Override
    public List<CourseFileResourceDto> getList(CourseFileResourceDto courseFileResourceDto) {
        return courseResourceDao.getList(courseFileResourceDto);
    }

    @Override
    public CourseFileResourceDto getById(Long id) {
        return courseResourceDao.getById(id);
    }
}
