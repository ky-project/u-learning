package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 课程文件dao
 *
 * @author luyuhao
 * @since 2020/02/14 02:48
 */
@Mapper
@Repository
public interface CourseFileDao {

    /**
     * 插入课程文件记录
     *
     * @param courseFileDto 待插入的课程文件对象
     */
    void insert(CourseFileDto courseFileDto);

    /**
     * 根据id查询课程文件信息
     *
     * @param id 课程文件id
     * @return 课程文件对象
     */
    CourseFileEntity getById(Long id);

    /**
     * 更新课程文件信息
     *
     * @param courseFileDto 待更新的课程文件对象
     */
    void update(CourseFileDto courseFileDto);

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
     * 根据parentId和文件名查询课程文件信息
     *
     * @param parentId 父节点id
     * @param fileName 文件名
     * @return 课程文件对象
     */
    CourseFileEntity getByParentIdAndFileName(@Param("parentId") Long parentId,
                                              @Param("fileName") String fileName);

    /**
     * 根据id更新valid值
     *
     * @param id       id
     * @param updateBy 更新者
     * @param valid    有效位的值
     */
    void updateValidById(@Param("id") Long id,
                         @Param("updateBy") String updateBy,
                         @Param("valid") Integer valid);
}