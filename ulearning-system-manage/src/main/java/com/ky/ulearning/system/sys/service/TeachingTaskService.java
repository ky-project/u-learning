package com.ky.ulearning.system.sys.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;

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

    /**
     * 分页查询教学任务信息
     *
     * @param teachingTaskDto 筛选参数
     * @param pageParam       分页参数
     * @return 封装教学任务信息的分页对象
     */
    PageBean<TeachingTaskEntity> pageTeachingTaskList(TeachingTaskDto teachingTaskDto, PageParam pageParam);
}
