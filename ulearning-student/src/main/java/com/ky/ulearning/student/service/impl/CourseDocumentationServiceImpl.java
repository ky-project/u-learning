package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.dao.CourseDocumentationDao;
import com.ky.ulearning.student.dao.CourseFileDao;
import com.ky.ulearning.student.dao.TeachingTaskDao;
import com.ky.ulearning.student.service.CourseDocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String teachingTaskRootFolderName = teachingTaskDto.getTeachingTaskAlias() + "#" + teachingTaskId;
        CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationDao.getByCourseIdAndFileName(teachingTaskDto.getCourseId(), MicroConstant.ROOT_FOLDER);
        if(StringUtil.isEmpty(courseFileDocumentationDto)){
            throw new BadRequestException(StudentErrorCodeEnum);
        }

    }
}
