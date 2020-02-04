package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.teacher.dto.ExperimentDto;

/**
 * 实验service - 接口类
 *
 * @author luyuhao
 * @since 20/02/04 21:00
 */
public interface TeachingTaskExperimentService {
    /**
     * 添加实验
     *
     * @param experimentDto 待添加的实验对象
     */
    void save(ExperimentDto experimentDto);
}
