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

    /**
     * 删除实验
     *
     * @param id       实验id
     * @param updateBy 更新者
     */
    void delete(Long id, String updateBy);

    /**
     * 根据教师id查询实验数量
     *
     * @param teaId 教师id
     * @return 实验数量
     */
    Integer getExperimentNumber(Long teaId);

    /**
     * 更新是否分享实验字段
     *
     * @param id               实验id
     * @param experimentShared 是否分享
     * @param updateBy         是否更新
     */
    void updateShared(Long id, Boolean experimentShared, String updateBy);
}
