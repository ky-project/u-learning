package com.ky.ulearning.monitor.dao;

import com.ky.ulearning.spi.monitor.dto.ActivityDto;
import com.ky.ulearning.spi.monitor.entity.ActivityEntity;
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
     * @param activityDto 动态对象
     */
    void insert(ActivityDto activityDto);

    /**
     * 根据id查询动态信息
     *
     * @param id 动态id
     * @return 动态对象
     */
    ActivityEntity getById(Long id);

    /**
     * 更新动态
     *
     * @param activityDto 待更新的动态对象
     */
    void update(ActivityDto activityDto);

}
