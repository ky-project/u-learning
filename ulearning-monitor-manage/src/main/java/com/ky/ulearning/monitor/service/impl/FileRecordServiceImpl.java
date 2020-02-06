package com.ky.ulearning.monitor.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.monitor.dao.FileRecordDao;
import com.ky.ulearning.monitor.service.FileRecordService;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luyuhao
 * @since 20/02/06 17:05
 */
@Service
@CacheConfig(cacheNames = "fileRecord")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class FileRecordServiceImpl extends BaseService implements FileRecordService {

    @Autowired
    private FileRecordDao fileRecordDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void insert(FileRecordDto fileRecordDto) {
        fileRecordDao.insert(fileRecordDto);
    }
}
