package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import org.apache.ibatis.annotations.Mapper;
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

}