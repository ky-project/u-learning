package com.ky.ulearning.monitor.logging.service;

import com.ky.ulearning.spi.monitor.logging.entity.LogEntity;
import org.springframework.scheduling.annotation.Async;

/**
 * 日志 service 接口类
 *
 * @author luyuhao
 * @date 19/12/05 03:00
 */
public interface LogService {
    /**
     * 插入记录
     *
     * @param logEntity 待插入的日志对象
     */
    @Async
    void insert(LogEntity logEntity);
}
