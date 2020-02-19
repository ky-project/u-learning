package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.StringUtil;
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
    public Long getByCourseIdAndUsername(Long courseId, String username) {
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
            return teacherCourseFileDto.getId();
        } else {
            //获取教师根节点
            CourseFileEntity teacherCourseFileEntity = courseFileDao.getByParentIdAndFileName(courseFileEntity.getId(), username);
            //如果教师根目录不存，初始化
            if (StringUtil.isEmpty(teacherCourseFileEntity)) {
                CourseFileDto teacherCourseFileDto = createFolder(courseId, username, courseFileEntity.getId());
                courseFileDao.insert(teacherCourseFileDto);
                return teacherCourseFileDto.getId();
            } else {
                return teacherCourseFileEntity.getId();
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
