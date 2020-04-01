package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.common.entity.ActivityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
}
