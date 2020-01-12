package com.ky.ulearning.monitor.logging.service.impl;

import com.ky.ulearning.monitor.logging.dao.LogDao;
import com.ky.ulearning.monitor.logging.service.LogService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.logging.dto.LogDto;
import com.ky.ulearning.spi.monitor.logging.entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class LogServiceImpl implements LogService {

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
        if (pageParam.getPageSize() != null && pageParam.getCurrentPage() != null) {
            //设置当前页
            pageBean.setCurrentPage(pageParam.getCurrentPage())
                    //设置页大小
                    .setPageSize(pageParam.getPageSize())
                    //设置总页数 {(总记录数 + 页大小 - 1) / 页大小}
                    .setTotalPage((pageBean.getTotal() + pageBean.getPageSize() - 1) / pageBean.getPageSize())
                    //设置是否有后一页
                    .setHasNext(pageBean.getCurrentPage() < pageBean.getTotalPage())
                    //设置是否有前一页
                    .setHasPre(pageBean.getCurrentPage() > 1);
        }
        return pageBean;
    }
}
