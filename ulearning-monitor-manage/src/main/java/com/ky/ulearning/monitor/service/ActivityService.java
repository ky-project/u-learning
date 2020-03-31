package com.ky.ulearning.monitor.service;

import com.ky.ulearning.spi.monitor.entity.ActivityEntity;

/**
 * 动态service - 接口
 *
 * @author luyuhao
 * @since 2020/04/01 00:05
 */
public interface ActivityService {

    /**
     * 插入教师动态
     * @param activityEntity 动态信息
     */
    void insertTeacherActivity(ActivityEntity activityEntity);
}
