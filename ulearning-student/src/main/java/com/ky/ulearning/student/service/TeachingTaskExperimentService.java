package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;

/**
 * 实验service - 接口
 *
 * @author luyuhao
 * @since 20/03/05 00:54
 */
public interface TeachingTaskExperimentService {

    /**
     * 分页查询实验信息
     *
     * @param pageParam     分页参数
     * @param experimentDto 筛选条件
     * @return 返回封装实验信息的分页对象
     */
    PageBean<TeachingTaskExperimentDto> pageList(ExperimentDto experimentDto, PageParam pageParam);

    /**
     * 根据id查询实验信息
     *
     * @param id 实验id
     * @return 实验对象
     */
    TeachingTaskExperimentDto getById(Long id);
}
