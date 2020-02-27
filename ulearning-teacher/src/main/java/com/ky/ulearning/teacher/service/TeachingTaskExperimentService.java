package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskExperimentEntity;

import java.util.List;

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

    /**
     * 根据id查询实验信息
     *
     * @param id 实验id
     * @return 实验对象
     */
    TeachingTaskExperimentDto getById(Long id);

    /**
     * 分页查询实验信息
     *
     * @param pageParam     分页参数
     * @param experimentDto 筛选条件
     * @return 返回封装实验信息的分页对象
     */
    PageBean<TeachingTaskExperimentDto> pageList(PageParam pageParam, ExperimentDto experimentDto);

    /**
     * 更新实验信息
     *
     * @param experimentDto 待更新的实验对象
     */
    void update(ExperimentDto experimentDto);

    /**
     * 根据教学任务id查询试验集合
     *
     * @param teachingTaskId 教学任务id
     * @return 试验信息集合
     */
    List<TeachingTaskExperimentEntity> listByTeachingTaskId(Long teachingTaskId);
}
