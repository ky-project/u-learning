package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.teacher.dto.CourseDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    CourseFileDocumentationDto getById(Long id);

    /**
     * 更新文件资料
     *
     * @param courseDocumentationDto 待更新的文件资料
     */
    void update(CourseDocumentationDto courseDocumentationDto);

    /**
     * 查询文件资料集合
     *
     * @param courseFileDocumentationDto 筛选对象
     * @return 返回课程文件资料集合
     */
    List<CourseFileDocumentationDto> getList(CourseFileDocumentationDto courseFileDocumentationDto);

    /**
     * 根据文件id查询课程文件资料对象
     *
     * @param fileId 文件id
     * @return 课程文件资料对象
     */
    CourseFileDocumentationDto getByFileId(Long fileId);

    /**
     * 根据id更新valid值
     *
     * @param id       id
     * @param updateBy 更新者
     * @param valid    有效位的值
     */
    void updateValidById(@Param("id") Long id, @Param("updateBy") String updateBy, @Param("valid") Integer valid);
}