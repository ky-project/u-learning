package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;

/**
 * 实验结果service - 接口
 *
 * @author luyuhao
 * @since 2020/3/9 21:57
 */
public interface ExperimentResultService {

    /**
     * 分页查询实验结果
     *
     * @param pageParam           分页参数
     * @param experimentResultDto 筛选条件
     * @return 实验结果集合
     */
    PageBean<ExperimentResultDto> pageList(PageParam pageParam, ExperimentResultDto experimentResultDto);

    /**
     * 根据id查询实验结果
     *
     * @param id 实验结果id
     * @return 实验结果对象
     */
    ExperimentResultDto getById(Long id);

    /**
     * 批改实验结果
     *
     * @param experimentResultEntity 实验对象
     */
    void update(ExperimentResultEntity experimentResultEntity);

    /**
     * 分享展示实验结果
     *
     * @param id               实验结果id
     * @param username         更新者
     * @param experimentShared 是否分享展示
     */
    void sharedExperimentResult(Long id, String username, Boolean experimentShared);
}
