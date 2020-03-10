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
    PageBean<TeachingTaskNoticeDto> pageList(PageParam pageParam, TeachingTaskNoticeDto teachingTaskNoticeDto);

    /**
     * 插入通告记录
     *
     * @param teachingTaskNoticeDto 待插入的通告记录
     */
    void save(TeachingTaskNoticeDto teachingTaskNoticeDto);

    /**
     * 根据id查询通告信息
     *
     * @param id 通告id
     * @return 通告对象
     */
    TeachingTaskNoticeEntity getById(Long id);

    /**
     * 更新通告信息
     *
     * @param teachingTaskNoticeDto 待更新的通告信息
     */
    void update(TeachingTaskNoticeDto teachingTaskNoticeDto);

    /**
     * 删除通告
     *
     * @param id       通告id
     * @param updateBy 更新者
     */
    void delete(Long id, String updateBy);
}
