package com.ky.ulearning.system.sys.dao;

import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 教学任务 dao
 *
 * @author luyuhao
 * @since 2020/01/16 00:16
 */
@Mapper
@Repository
public interface TeachingTaskDao {

    /**
     * 插入教学任务信息
     *
     * @param teachingTaskDto 教学任务信息
     */
    void insert(TeachingTaskDto teachingTaskDto);

    void update(TeachingTaskDto teachingTaskDto);
}