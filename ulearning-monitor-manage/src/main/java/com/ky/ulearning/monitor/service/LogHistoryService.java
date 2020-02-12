package com.ky.ulearning.monitor.service;

import com.ky.ulearning.spi.monitor.dto.LogHistoryDto;
import com.ky.ulearning.spi.monitor.entity.LogHistoryEntity;
import com.ky.ulearning.spi.monitor.vo.LogHistoryVo;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/10 21:04
 */
public interface LogHistoryService {

    /**
     * 添加历史日志记录
     *
     * @param logHistoryDto 待添加的历史日志对象
     */
    void save(LogHistoryDto logHistoryDto);

    /**
     * 查询所有历史日志
     *
     * @return 历史日志vo集合
     */
    List<LogHistoryVo> getVoList();

    /**
     * 根据id查询历史日志
     *
     * @param id id
     * @return 历史日志对象
     */
    LogHistoryEntity getById(Long id);
}
