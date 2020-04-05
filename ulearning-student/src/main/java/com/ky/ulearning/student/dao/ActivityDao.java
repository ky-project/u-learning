package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.entity.ActivityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 动态dao
 *
 * @author luyuhao
 * @since 2020/04/01 00:00
 */
@Mapper
@Repository
public interface ActivityDao {

    /**
     * 插入动态
     *
     * @param activityEntity 动态对象
     */
    void insert(ActivityEntity activityEntity);

    /**
     * 查询教师动态集合
     *
     * @param pageParam 分页参数
     * @param stuId     学生id
     * @return 教师动态集合
     */
    List<ActivityEntity> listPage(@Param("stuId") Long stuId, @Param("pageParam") PageParam pageParam);

    /**
     * 查询教师动态集合 - 总记录
     *
     * @param stuId 学生id
     * @return 总记录
     */
    Integer countListPage(Long stuId);
}
