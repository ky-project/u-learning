package com.ky.ulearning.monitor.logging.service.impl;

import com.ky.ulearning.monitor.logging.dao.LogDao;
import com.ky.ulearning.monitor.logging.service.LogService;
import com.ky.ulearning.spi.monitor.logging.entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 日志 service 实现类
 *
 * @author luyuhao
 * @date 19/12/05 03:01
 */
@Service
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void insert(LogEntity logEntity) {
        logDao.insert(logEntity);
    }
}
