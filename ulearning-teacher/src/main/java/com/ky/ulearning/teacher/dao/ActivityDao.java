package com.ky.ulearning.teacher.dao;

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
     * 分页查询学生动态
     *
     * @param pageParam 分页参数
     * @param teaId     教师id
     * @return 学生动态集合
     */
    List<ActivityEntity> listPage(@Param("teaId") Long teaId, @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询学生动态 - 总记录
     *
     * @param teaId 教师id
     * @return 总记录
     */
    Integer countListPage(@Param("teaId") Long teaId);
}
