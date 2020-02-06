package com.ky.ulearning.monitor.service;

import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import org.springframework.scheduling.annotation.Async;

/**
 * 文件记录service - 接口类
 *
 * @author luyuhao
 * @since 20/02/06 17:04
 */
public interface FileRecordService {

    /**
     * 插入文件记录
     *
     * @param fileRecordDto 待插入的文件记录
     */
    @Async
    void insert(FileRecordDto fileRecordDto);
}
