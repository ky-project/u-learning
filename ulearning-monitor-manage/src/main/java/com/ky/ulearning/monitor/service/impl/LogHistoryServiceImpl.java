package com.ky.ulearning.monitor.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.monitor.dao.LogHistoryDao;
import com.ky.ulearning.monitor.service.LogHistoryService;
import com.ky.ulearning.spi.monitor.dto.LogHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luyuhao
 * @since 20/02/10 21:04
 */
@Service
@CacheConfig(cacheNames = "logHistory")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class LogHistoryServiceImpl extends BaseService implements LogHistoryService {

    @Autowired
    private LogHistoryDao logHistoryDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void save(LogHistoryDto logHistoryDto) {
        logHistoryDao.insert(logHistoryDto);
    }
}
