package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.teacher.dto.CourseDocumentationDto;
import com.ky.ulearning.spi.teacher.entity.CourseDocumentationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 文件资料dao
 *
 * @author luyuhao
 * @since 2020/02/14 20:23
 */
@Mapper
@Repository
public interface CourseDocumentationDao {

    /**
     * 插入文件资料记录
     *
     * @param courseDocumentationDto 待插入的文件资料对象
     */
    void insert(CourseDocumentationDto courseDocumentationDto);

    /**
     * 根据id查询文件资料
     *
     * @param id 文件资料id
     * @return 文件资料实体类
     */
    CourseDocumentationEntity getById(Long id);

    /**
     * 更新文件资料
     *
     * @param courseDocumentationDto 待更新的文件资料
     */
    void update(CourseDocumentationDto courseDocumentationDto);

}