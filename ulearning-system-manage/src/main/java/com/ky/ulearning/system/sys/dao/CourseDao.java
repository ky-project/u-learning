package com.ky.ulearning.system.sys.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.CourseDto;
import com.ky.ulearning.spi.system.entity.CourseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程dao
 *
 * @author luyuhao
 * @since 2020/01/13 23:39
 */
@Mapper
@Repository
public interface CourseDao {

    /**
     * 插入课程信息
     *
     * @param course 课程对象
     */
    void insert(CourseEntity course);

    /**
     * 根据id获取课程对象
     *
     * @param id 课程id
     * @return 返回课程对象
     */
    CourseEntity getById(Long id);

    /**
     * 更新课程信息
     *
     * @param course 课程对象
     */
    void update(CourseEntity course);

    /**
     * 分页查询课程信息
     *
     * @param courseDto 筛选对象
     * @param pageParam 分页参数
     * @return 课程列表
     */
    List<CourseEntity> listPage(@Param("courseDto") CourseDto courseDto, @Param("pageParam") PageParam pageParam);

    /**
     * 根据筛选条件查询总记录数
     *
     * @param courseDto 筛选参数
     * @return 总记录数
     */
    Integer countListPage(@Param("courseDto")CourseDto courseDto);
}