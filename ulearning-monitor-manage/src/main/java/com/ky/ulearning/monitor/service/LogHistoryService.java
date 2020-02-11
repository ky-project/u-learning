package com.ky.ulearning.monitor.service;

import com.ky.ulearning.spi.monitor.dto.LogHistoryDto;

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
}
