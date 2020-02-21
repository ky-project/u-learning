package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import com.ky.ulearning.spi.teacher.dto.CourseResourceDto;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/17 19:25
 */
public interface CourseResourceService {

    /**
     * 新增教学资料
     *
     * @param courseResourceDto 教学资料对象
     * @param courseFileDto     课程文件对象
     */
    void save(CourseResourceDto courseResourceDto, CourseFileDto courseFileDto);

    /**
     * 根据文件id查询课程文件教学资源对象
     *
     * @param fileId 文件id
     * @return 课程文件资料对象
     */
    CourseFileResourceDto getByFileId(Long fileId);

    /**
     * 查询教学资源集合
     *
     * @param courseFileResourceDto 筛选对象
     * @return 返回课程文件教学资源集合
     */
    List<CourseFileResourceDto> getList(CourseFileResourceDto courseFileResourceDto);

    /**
     * 根据id查询课程文件教学资源对象
     *
     * @param id id
     * @return 课程文件教学资源对象
     */
    CourseFileResourceDto getById(Long id);

    /**
     * 更新课程文件教学资源
     *
     * @param courseFileResourceDto 课程文件教学资源对象
     */
    void update(CourseFileResourceDto courseFileResourceDto);

    /**
     * 根据id和fileId删除课程文件教学资源
     *
     * @param id       教学资源id
     * @param fileId   课程文件id
     * @param updateBy 更新者
     */
    void delete(Long id, Long fileId, String updateBy);

    /**
     * 根据课程id和工号查询id
     *
     * @param courseId 课程id
     * @param username 工号
     * @return id
     */
    CourseFileResourceDto getByCourseIdAndUsername(Long courseId, String username);

    /**
     * 根据父节点id查询所有课程文件教学资源
     *
     * @param fileParentId 父节点id
     * @return 课程文件教学资源
     */
    List<CourseFileResourceDto> getListByFileParentId(Long fileParentId);
}
