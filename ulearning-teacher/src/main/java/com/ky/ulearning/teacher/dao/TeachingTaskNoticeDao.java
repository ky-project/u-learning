package com.ky.ulearning.teacher.dao;


import com.ky.ulearning.spi.teacher.dto.TeachingTaskNoticeDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
     * 插入通告
     *
     * @param teachingTaskNoticeDto 待插入的通告对象
     */
    void insert(TeachingTaskNoticeDto teachingTaskNoticeDto);

    /**
     * 根据id获取通告记录
     *
     * @param id 通告id
     * @return 通告对象
     */
    TeachingTaskNoticeEntity getById(Long id);

    /**
     * 更新通告记录
     *
     * @param teachingTaskNoticeDto 待更新的通告
     */
    void update(TeachingTaskNoticeDto teachingTaskNoticeDto);

}