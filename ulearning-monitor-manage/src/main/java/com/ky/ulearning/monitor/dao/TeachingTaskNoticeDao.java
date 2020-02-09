package com.ky.ulearning.monitor.dao;


import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教学任务通告dao
 *
 * @author luyuhao
 * @since 2020/01/30 23:32
 */
@Mapper
@Repository
public interface TeachingTaskNoticeDao {

    /**
     * 获取所有教学任务通告
     *
     * @return 教学任务通告集合
     */
    List<TeachingTaskNoticeEntity> getByNoticeAttachment();
}