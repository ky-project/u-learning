package com.ky.ulearning.monitor.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.utils.DateUtil;
import com.ky.ulearning.monitor.dao.LogDao;
import com.ky.ulearning.monitor.service.LogService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.LogDto;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import com.ky.ulearning.spi.monitor.vo.TrafficVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日志 service 实现类
 *
 * @author luyuhao
 * @date 19/12/05 03:01
 */
@Service
@Transactional(readOnly = true, rollbackFor = Throwable.class)
public class LogServiceImpl extends BaseService implements LogService {

    private static final int MAX_TRAFFIC_DAYS = 30;

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
    public List<String> getLogType() {
        return logDao.getLogType();
    }

    @Override
    public Date getFirstCreateTimeLessOrEqual(String dateTime) {
        return logDao.getFirstCreateTimeLessOrEqual(dateTime);
    }

    @Override
    public List<LogEntity> getByDate(String date) {
        return logDao.getByDate(date);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void deleteByDate(String date) {
        logDao.deleteByDate(date);
    }

    @Override
    public TrafficVo getTodayUserNumber(String today) {
        Long todayUserNumber = logDao.getTodayUserNumber(today);
        return new TrafficVo(today, todayUserNumber);
    }

    @Override
    public List<TrafficVo> getTrafficByDate(Date today, Date oldDate) {
        List<TrafficVo> trafficVoList = new ArrayList<>();
        int index = 0;
        DateTime indexDate;
        do {
            //最多查询MAX_TRAFFIC_DAYS天的访问记录
            if (index >= MAX_TRAFFIC_DAYS) {
                throw new BadRequestException("最多查询 " + MAX_TRAFFIC_DAYS + " 天的访问记录");
            }
            indexDate = DateUtil.offsetDay(oldDate, index++);
            //得到indexDate对应的访问量
            Long todayUserNumber = logDao.getTodayUserNumber(DateUtil.format(indexDate, DatePattern.NORM_DATE_PATTERN));
            TrafficVo trafficVo = new TrafficVo(DateUtil.format(indexDate, DatePattern.NORM_DATE_PATTERN), todayUserNumber);
            trafficVoList.add(trafficVo);
        } while (!DateUtil.isSameDay(today, indexDate));
        return trafficVoList;
    }
}
