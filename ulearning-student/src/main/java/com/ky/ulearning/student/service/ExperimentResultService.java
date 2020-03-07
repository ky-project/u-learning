package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;

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
}
