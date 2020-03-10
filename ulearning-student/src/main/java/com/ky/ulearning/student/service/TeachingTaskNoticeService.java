package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskNoticeDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;

import java.util.Set;

/**
 * @author luyuhao
 * @since 20/02/22 17:07
 */
public interface TeachingTaskNoticeService {

    /**
     * 分页查询通告内容
     *
     * @param pageParam             分页参数
     * @param teachingTaskNoticeDto 查询条件
     * @return 返回封装通知对象的分页对象
     */
    PageBean<TeachingTaskNoticeDto> pageList(PageParam pageParam, TeachingTaskNoticeDto teachingTaskNoticeDto);

    /**
     * 根据教学任务id集合查询所有通告id集合
     *
     * @param teachingTaskIdSet 教学任务id集合
     * @return 通告id集合
     */
    Set<Long> getIdSetByTeachingTaskIdSet(Set<Long> teachingTaskIdSet);

    /**
     * 根据id查询通告信息
     *
     * @param id id
     * @return 通告信息对象
     */
    TeachingTaskNoticeEntity getById(Long id);
}
