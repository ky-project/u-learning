package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.system.entity.CourseEntity;
import com.ky.ulearning.spi.system.vo.CourseVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/01/26 21:32
 */
@Mapper
@Repository
public interface CourseDao {

    /**
     * 查询所有课程信息
     *
     * @return 课程vo对象集合
     */
    List<CourseVo> getAllVo();

    /**
     * 根据id获取课程对象
     *
     * @param id 课程id
     * @return 返回课程对象
     */
    CourseEntity getById(Long id);
}
