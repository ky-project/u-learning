package com.ky.ulearning.teacher.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.teacher.dto.CourseDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.teacher.common.utils.CourseFileUtil;
import com.ky.ulearning.teacher.dao.CourseDocumentationDao;
import com.ky.ulearning.teacher.dao.CourseFileDao;
import com.ky.ulearning.teacher.dao.TeachingTaskDao;
import com.ky.ulearning.teacher.service.CourseDocumentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    @Autowired
    private TeachingTaskDao teachingTaskDao;

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
    public CourseFileDocumentationDto getByCourseIdAndUsername(Long courseId, String username, Long teachingTaskId) {
        //获取课程根节点
        CourseFileEntity courseFileEntity = courseFileDao.getByCourseIdAndFileName(courseId, MicroConstant.ROOT_FOLDER);
        CourseTeachingTaskDto courseTeachingTaskDto = teachingTaskDao.getById(teachingTaskId);
        String teachingTaskAlias = "[" + courseTeachingTaskDto.getTerm() + "]" + courseTeachingTaskDto.getTeachingTaskAlias() + "#" + courseTeachingTaskDto.getId();
        //若课程根目录不存在，初始化
        if (StringUtil.isEmpty(courseFileEntity)) {
            //创建课程根文件夹
            CourseFileDto rootCourseFileDto = createFolder(courseId, MicroConstant.ROOT_FOLDER, MicroConstant.ROOT_FOLDER_PARENT_ID, null);
            courseFileDao.insert(rootCourseFileDto);
            //创建用户根文件夹
            CourseFileDto teacherCourseFileDto = createFolder(courseId, username, rootCourseFileDto.getId(), null);
            courseFileDao.insert(teacherCourseFileDto);
            CourseDocumentationDto courseDocumentationDtoFolder = CourseFileUtil.createCourseDocumentationDtoFolder(username);
            courseDocumentationDtoFolder.setFileId(teacherCourseFileDto.getId());
            courseDocumentationDao.insert(courseDocumentationDtoFolder);
            //创建教学任务根文件夹
            CourseFileDto teachingTaskCourseFileDto = createFolder(courseId, teachingTaskAlias, teacherCourseFileDto.getId(), username);
            courseFileDao.insert(teachingTaskCourseFileDto);
            CourseDocumentationDto courseDocumentationDtoTeachingTaskFolder = CourseFileUtil.createCourseDocumentationDtoFolder(username);
            courseDocumentationDtoTeachingTaskFolder.setFileId(teachingTaskCourseFileDto.getId());
            courseDocumentationDao.insert(courseDocumentationDtoTeachingTaskFolder);
            return courseDocumentationDao.getById(courseDocumentationDtoTeachingTaskFolder.getId());
        } else {
            //获取教师根节点
            CourseFileEntity teacherCourseFileEntity = courseFileDao.getByParentIdAndFileName(courseFileEntity.getId(), username);
            //如果教师根目录不存，初始化
            if (StringUtil.isEmpty(teacherCourseFileEntity)) {
                CourseFileDto teacherCourseFileDto = createFolder(courseId, username, courseFileEntity.getId(), null);
                courseFileDao.insert(teacherCourseFileDto);
                CourseDocumentationDto courseDocumentationDtoFolder = CourseFileUtil.createCourseDocumentationDtoFolder(username);
                courseDocumentationDtoFolder.setFileId(teacherCourseFileDto.getId());
                //插入文件资料对象
                courseDocumentationDao.insert(courseDocumentationDtoFolder);
                //创建教学任务根文件夹
                CourseFileDto teachingTaskCourseFileDto = createFolder(courseId, teachingTaskAlias, teacherCourseFileDto.getId(), username);
                courseFileDao.insert(teachingTaskCourseFileDto);
                CourseDocumentationDto courseDocumentationDtoTeachingTaskFolder = CourseFileUtil.createCourseDocumentationDtoFolder(username);
                courseDocumentationDtoTeachingTaskFolder.setFileId(teachingTaskCourseFileDto.getId());
                courseDocumentationDao.insert(courseDocumentationDtoTeachingTaskFolder);
                return courseDocumentationDao.getById(courseDocumentationDtoTeachingTaskFolder.getId());
            } else {
                CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationDao.getByFileId(teacherCourseFileEntity.getId());
                //当用户根目录已创建，但文件资料未索引，创建索引
                if (StringUtil.isEmpty(courseFileDocumentationDto)) {
                    CourseDocumentationDto courseDocumentationDtoFolder = CourseFileUtil.createCourseDocumentationDtoFolder(username);
                    courseDocumentationDtoFolder.setFileId(teacherCourseFileEntity.getId());
                    //插入文件资料对象
                    courseDocumentationDao.insert(courseDocumentationDtoFolder);
                }
                //查询教学任务文件夹
                CourseFileEntity teachingTaskCourseFileEntity = courseFileDao.getByParentIdAndFileName(teacherCourseFileEntity.getId(), teachingTaskAlias);
                //教学任务文件夹是否为空
                if (StringUtil.isEmpty(teachingTaskCourseFileEntity)) {
                    //创建教学任务文件夹
                    CourseFileDto teachingTaskCourseFileDto = createFolder(courseId, teachingTaskAlias, teacherCourseFileEntity.getId(), username);
                    courseFileDao.insert(teachingTaskCourseFileDto);
                    CourseDocumentationDto courseDocumentationDtoTeachingTaskFolder = CourseFileUtil.createCourseDocumentationDtoFolder(username);
                    courseDocumentationDtoTeachingTaskFolder.setFileId(teachingTaskCourseFileDto.getId());
                    courseDocumentationDao.insert(courseDocumentationDtoTeachingTaskFolder);
                    return courseDocumentationDao.getById(courseDocumentationDtoTeachingTaskFolder.getId());
                } else {
                    CourseFileDocumentationDto teachingTaskCourseFileDocumentationDto = courseDocumentationDao.getByFileId(teachingTaskCourseFileEntity.getId());
                    //当教学任务根目录已创建，但文件资料未索引，创建索引
                    if (StringUtil.isEmpty(teachingTaskCourseFileDocumentationDto)) {
                        CourseDocumentationDto courseDocumentationDto = CourseFileUtil.createCourseDocumentationDtoFolder(username);
                        courseDocumentationDto.setFileId(teachingTaskCourseFileEntity.getId());
                        courseDocumentationDao.insert(courseDocumentationDto);
                        return courseDocumentationDao.getById(courseDocumentationDto.getId());
                    }
                    return teachingTaskCourseFileDocumentationDto;
                }
            }
        }
    }

    /**
     * 创建课程文件初始化对象
     */
    private CourseFileDto createFolder(Long courseId, String fileName, Long parentId, String username) {
        CourseFileDto courseFileDto = new CourseFileDto();
        courseFileDto.setCourseId(courseId);
        courseFileDto.setFileName(fileName);
        courseFileDto.setFileType(MicroConstant.FOLDER_TYPE);
        courseFileDto.setFileParentId(parentId);
        courseFileDto.setCreateBy(StringUtil.isEmpty(username) ? "system" : username);
        courseFileDto.setUpdateBy(StringUtil.isEmpty(username) ? "system" : username);
        return courseFileDto;
    }

    @Override
    public List<CourseFileDocumentationDto> getListByFileParentIdAndFileType(Long fileParentId, Integer fileType) {
        return Optional.ofNullable(courseDocumentationDao.getListByFileParentIdAndFileType(fileParentId, fileType))
                .orElse(Collections.emptyList());
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void updateShared(Long id, Boolean documentationShared, String updateBy) {
        CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationDao.getById(id);
        //文件/文件夹分别处理
        if ((new Integer(MicroConstant.FILE_TYPE)).equals(courseFileDocumentationDto.getFileType())) {
            //文件处理
            if (documentationShared) {
                //分享，向上遍历更新
                upShared(courseFileDocumentationDto.getFileParentId(), documentationShared, updateBy);
            }
        } else if ((new Integer(MicroConstant.FOLDER_TYPE)).equals(courseFileDocumentationDto.getFileType())) {
            //文件夹处理
            if (documentationShared) {
                //分享，向上/下遍历更新
                upShared(courseFileDocumentationDto.getFileParentId(), documentationShared, updateBy);
                downShared(courseFileDocumentationDto.getFileId(), documentationShared, updateBy);
            } else {
                //取消分享，向下遍历
                downShared(courseFileDocumentationDto.getFileId(), documentationShared, updateBy);
            }
        }
        //更新当前文件资料
        courseDocumentationDao.updateSharedById(id, documentationShared, updateBy);
    }

    /**
     * 向上层遍历修改共享值
     */
    private void upShared(Long fileParentId, Boolean documentationShared, String updateBy) {
        List<Long> idList = new ArrayList<>();
        while (true) {
            CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationDao.getByFileId(fileParentId);
            if (StringUtil.isEmpty(courseFileDocumentationDto) || courseFileDocumentationDto.getFileParentId().equals(MicroConstant.ROOT_FOLDER_PARENT_ID)) {
                break;
            }
            fileParentId = courseFileDocumentationDto.getFileParentId();
            idList.add(courseFileDocumentationDto.getId());
        }
        if (!CollectionUtils.isEmpty(idList)) {
            //更新共享值
            courseDocumentationDao.updateSharedByIds(idList, documentationShared, updateBy);
        }
    }

    /**
     * 向下层遍历修改共享值
     */
    private void downShared(Long fileId, Boolean documentationShared, String updateBy) {
        List<Long> idList = new ArrayList<>();
        List<Long> fileParenIdList = new LinkedList<>();
        fileParenIdList.add(fileId);
        while (!CollectionUtils.isEmpty(fileParenIdList)) {
            List<CourseFileDocumentationDto> courseFileDocumentationDtoList = courseDocumentationDao.getListByFileParentId(fileParenIdList.get(0));
            fileParenIdList.remove(0);
            if (CollectionUtils.isEmpty(courseFileDocumentationDtoList)) {
                continue;
            }
            for (CourseFileDocumentationDto courseFileDocumentationDto : courseFileDocumentationDtoList) {
                //文件夹加入队列继续遍历
                if ((new Integer(MicroConstant.FOLDER_TYPE)).equals(courseFileDocumentationDto.getFileType())) {
                    fileParenIdList.add(courseFileDocumentationDto.getFileId());
                }
                idList.add(courseFileDocumentationDto.getId());
            }
        }
        if (!CollectionUtils.isEmpty(idList)) {
            //更新共享值
            courseDocumentationDao.updateSharedByIds(idList, documentationShared, updateBy);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public Long getSharedByCourseId(Long courseId) {
        //获取课程根节点
        CourseFileEntity courseFileEntity = courseFileDao.getByCourseIdAndFileName(courseId, MicroConstant.ROOT_FOLDER);
        //若课程根目录不存在，初始化
        if (StringUtil.isEmpty(courseFileEntity)) {
            //创建课程根文件夹
            CourseFileDto rootCourseFileDto = createFolder(courseId, MicroConstant.ROOT_FOLDER, MicroConstant.ROOT_FOLDER_PARENT_ID, null);
            courseFileDao.insert(rootCourseFileDto);
            return rootCourseFileDto.getId();
        }
        return courseFileEntity.getId();
    }

    @Override
    public List<CourseFileDocumentationDto> getSharedList(CourseFileDocumentationDto courseFileDocumentationDto) {
        return courseDocumentationDao.getSharedList(courseFileDocumentationDto);
    }
}
