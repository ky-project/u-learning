package com.ky.ulearning.monitor.logging.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.monitor.logging.dao.LogDao;
import com.ky.ulearning.monitor.logging.service.LogService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.logging.dto.LogDto;
import com.ky.ulearning.spi.monitor.logging.entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 日志 service 实现类
 *
 * @author luyuhao
 * @date 19/12/05 03:01
 */
@Service
@CacheConfig(cacheNames = "log")
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class LogServiceImpl extends BaseService implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void insert(LogEntity logEntity) {
        logDao.insert(logEntity);
    }

    @Override
    public PageBean<LogEntity> pageLogList(LogDto logDto, PageParam pageParam) {
        List<LogEntity> logList = logDao.listPage(logDto, pageParam);

        PageBean<LogEntity> pageBean = new PageBean<>();
        //设置总记录数
        pageBean.setTotal(logDao.countListPage(logDto))
                //设置查询结果
                .setContent(logList);
        return setPageBeanProperties(pageBean, pageParam);
    }

    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    public List<String> getLogType() {
        return logDao.getLogType();
    }
}
