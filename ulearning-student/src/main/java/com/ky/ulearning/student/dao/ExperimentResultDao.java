package com.ky.ulearning.student.dao;

import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 实验结果dao
 *
 * @author luyuhao
 * @since 2020/03/06 01:42
 */
@Mapper
@Repository
public interface ExperimentResultDao {

    /**
     * 插入记录
     *
     * @param experimentResultDto 待插入的对象
     */
    void insert(ExperimentResultDto experimentResultDto);

    /**
     * 根据id查询实验结果
     *
     * @param id id
     * @return 实验结果对象
     */
    ExperimentResultEntity getById(Long id);

    /**
     * 更新实验结果
     *
     * @param experimentResultDto 待更新的对象
     */
    void update(ExperimentResultDto experimentResultDto);

    /**
     * 根据实验id和学生id查询实验结果
     *
     * @param experimentId 实验id
     * @param stuId        学生id
     * @return 实验结果
     */
    ExperimentResultEntity getByExperimentIdAndStuId(@Param("experimentId") Long experimentId,
                                                    @Param("stuId") Long stuId);
}
