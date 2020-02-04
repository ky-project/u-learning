package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskExperimentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 实验dao
 *
 * @author luyuhao
 * @since 2020/02/04 20:53
 */
@Mapper
@Repository
public interface TeachingTaskExperimentDao {

    /**
     * 插入实验
     *
     * @param experimentDto 待插入实验对象
     */
    void insert(ExperimentDto experimentDto);

    /**
     * 根据id查询实验信息
     *
     * @param id 实验id
     * @return 实验对象
     */
    TeachingTaskExperimentEntity getById(Long id);

    /**
     * 更新实验信息
     *
     * @param experimentDto 待更新的实验对象
     */
    void update(ExperimentDto experimentDto);
}