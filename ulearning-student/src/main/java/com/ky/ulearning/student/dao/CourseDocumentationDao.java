package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/22 20:36
 */
@Mapper
@Repository
public interface CourseDocumentationDao {

    /**
     * 根据课程id和文件名查询文件资料对象
     *
     * @param courseId 课程id
     * @param fileName 文件名
     * @return 文件资料对象
     */
    CourseFileDocumentationDto getByCourseIdAndFileName(@Param("courseId") Long courseId,
                                                        @Param("fileName") String fileName);

    /**
     * 查询文件资料集合
     *
     * @param courseFileDocumentationDto 筛选对象
     * @return 返回课程文件资料集合
     */
    List<CourseFileDocumentationDto> getList(CourseFileDocumentationDto courseFileDocumentationDto);
}
