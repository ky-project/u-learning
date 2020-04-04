package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;

import java.util.List;

/**
 * 实验结果service - 接口
 *
 * @author luyuhao
 * @since 20/03/06 01:51
 */
public interface ExperimentResultService {

    /**
     * 根据实验id和学生id查询实验结果
     *
     * @param experimentId 实验id
     * @param stuId        学生id
     * @return 实验结果
     */
    ExperimentResultEntity getByExperimentIdAndStuId(Long experimentId, Long stuId);

    /**
     * 添加实验结果
     *
     * @param experimentResultDto 待添加的实验结果
     */
    void add(ExperimentResultDto experimentResultDto);

    /**
     * 根据id查询实验结果
     *
     * @param id id
     * @return 实验结果
     */
    ExperimentResultEntity getById(Long id);

    /**
     * 查询实验结果详细信息
     *
     * @param experimentId 实验id
     * @param stuId        学生id
     * @return 实验结果
     */
    ExperimentResultDto getDetailByExperimentIdAndStuId(Long experimentId, Long stuId);

    /**
     * 查询优秀实验作品
     *
     * @param experimentResultDto 筛选参数
     * @return 实验结果的集合
     */
    List<ExperimentResultDto> getList(ExperimentResultDto experimentResultDto);
}
