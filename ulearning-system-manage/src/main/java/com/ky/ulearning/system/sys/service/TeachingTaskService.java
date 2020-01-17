package com.ky.ulearning.system.sys.service;

import com.ky.ulearning.spi.system.dto.TeachingTaskDto;

/**
 * 教学任务service - 接口类
 *
 * @author luyuhao
 * @since 20/01/16 00:28
 */
public interface TeachingTaskService {

    /**
     * 插入教学任务信息
     *
     * @param teachingTaskDto 教学任务对象
     */
    void insert(TeachingTaskDto teachingTaskDto);
}
