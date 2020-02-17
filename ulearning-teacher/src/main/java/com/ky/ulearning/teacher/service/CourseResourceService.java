package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import com.ky.ulearning.spi.teacher.dto.CourseResourceDto;

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
     * 根据文件id查询课程文件资料对象
     *
     * @param fileId 文件id
     * @return 课程文件资料对象
     */
    CourseFileResourceDto getByFileId(Long fileId);
}
