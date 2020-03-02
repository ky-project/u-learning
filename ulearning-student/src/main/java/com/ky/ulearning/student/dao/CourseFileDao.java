package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author luyuhao
 * @since 20/02/22 20:36
 */
@Mapper
@Repository
public interface CourseFileDao {

    /**
     * 根据courseId和文件名查询课程文件信息
     *
     * @param courseId 课程id
     * @param fileName 文件名
     * @return 课程文件对象
     */
    CourseFileEntity getByCourseIdAndFileName(@Param("courseId") Long courseId,
                                              @Param("fileName") String fileName);

    /**
     * 根据id查询课程文件信息
     *
     * @param id 课程文件id
     * @return 课程文件对象
     */
    CourseFileEntity getById(Long id);
}
