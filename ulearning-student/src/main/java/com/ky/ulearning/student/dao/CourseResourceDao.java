package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/22 20:40
 */
@Mapper
@Repository
public interface CourseResourceDao {

    /**
     * 根据课程id和文件名查询教学资源对象
     *
     * @param courseId 课程id
     * @param fileName 文件名
     * @return 教学资源对象
     */
    CourseFileResourceDto getByCourseIdAndFileName(@Param("courseId") Long courseId,
                                                   @Param("fileName") String fileName);

    /**
     * 查询教学资源集合
     *
     * @param courseFileResourceDto 筛选对象
     * @return 返回课程教学资源集合
     */
    List<CourseFileResourceDto> getList(CourseFileResourceDto courseFileResourceDto);

    /**
     * 根据id查询教学资源信息
     *
     * @param id id
     * @return 教学资源对象
     */
    CourseFileResourceDto getById(Long id);
}
