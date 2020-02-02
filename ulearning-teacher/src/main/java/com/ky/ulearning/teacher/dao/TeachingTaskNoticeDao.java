package com.ky.ulearning.teacher.dao;


import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskNoticeDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 分页查询通告内容
     *
     * @param pageParam             分页参数
     * @param teachingTaskNoticeDto 筛选参数
     * @return 返回封装通知对象的分页对象
     */
    List<TeachingTaskNoticeEntity> listPage(@Param("teachingTaskNoticeDto") TeachingTaskNoticeDto teachingTaskNoticeDto,
                                            @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询通告内容 - 总记录
     *
     * @param teachingTaskNoticeDto 筛选参数
     * @return 总记录数
     */
    Integer countListPage(@Param("teachingTaskNoticeDto") TeachingTaskNoticeDto teachingTaskNoticeDto);

    /**
     * 更新valid值
     *
     * @param id       通告id
     * @param updateBy 更新者
     * @param valid    值
     */
    void updateValid(@Param("id") Long id, @Param("updateBy") String updateBy, @Param("valid") Integer valid);
}