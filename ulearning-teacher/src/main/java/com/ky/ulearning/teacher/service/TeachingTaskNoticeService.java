package com.ky.ulearning.teacher.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskNoticeDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;

/**
 * 通告service - 接口类
 *
 * @author luyuhao
 * @since 20/01/30 23:37
 */
public interface TeachingTaskNoticeService {

    /**
     * 分页查询通告内容
     *
     * @param pageParam             分页参数
     * @param teachingTaskNoticeDto 筛选参数
     * @return 返回封装通知对象的分页对象
     */
    PageBean<TeachingTaskNoticeEntity> pageList(PageParam pageParam, TeachingTaskNoticeDto teachingTaskNoticeDto);

    /**
     * 插入通告记录
     *
     * @param teachingTaskNoticeDto 待插入的通告记录
     */
    void save(TeachingTaskNoticeDto teachingTaskNoticeDto);
}
