package com.ky.ulearning.monitor.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.utils.DateUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.monitor.dao.LogDao;
import com.ky.ulearning.monitor.service.LogService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.LogDto;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import com.ky.ulearning.spi.monitor.vo.TrafficOperationVo;
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

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private LogDao logDao;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void insert(LogEntity logEntity) {
        if (StringUtil.isNotEmpty(logEntity.getLogUsername())
                || StringUtil.isNotEmpty(logEntity.getLogIp())) {
            logDao.insert(logEntity);
        }
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
    public List<TrafficVo> getTrafficByDate(Date today, Date oldDate) {
        List<TrafficVo> trafficVoList = new ArrayList<>();
        int index = 0;
        DateTime indexDate;
        do {
            //最多查询MAX_TRAFFIC_DAYS天的访问记录
            if (index >= defaultConfigParameters.getLogRetentionDays()) {
                break;
            }
            indexDate = DateUtil.offsetDay(oldDate, index++);
            //得到indexDate对应的访问量
            Long todayUserNumber = logDao.getTodayUserNumber(DateUtil.format(indexDate, DatePattern.NORM_DATE_PATTERN));
            TrafficVo trafficVo = new TrafficVo(DateUtil.format(indexDate, DatePattern.NORM_DATE_PATTERN), todayUserNumber);
            trafficVoList.add(trafficVo);
        } while (!DateUtil.isSameDay(today, indexDate));
        return trafficVoList;
    }

    @Override
    public List<LogEntity> getLogTop(Integer topNumber) {
        return logDao.getLogTop(topNumber);
    }

    @Override
    public TrafficOperationVo getDaysOperation(Date today, DateTime oldDate, String username) {
        TrafficOperationVo trafficOperationVo = new TrafficOperationVo();
        List<TrafficVo> totalOperation = new ArrayList<>();
        List<TrafficVo> selfOperation = new ArrayList<>();
        int index = 0;
        DateTime indexDate;
        do {
            //最多查询MAX_TRAFFIC_DAYS天的访问记录
            if (index >= defaultConfigParameters.getLogRetentionDays()) {
                break;
            }
            indexDate = DateUtil.offsetDay(oldDate, index++);
            //得到indexDate对应的所有用户操作数
            Long totalTraffic = logDao.getTodayOperationNumber(DateUtil.format(indexDate, DatePattern.NORM_DATE_PATTERN));
            TrafficVo totalTrafficVo = new TrafficVo(DateUtil.format(indexDate, DatePattern.NORM_DATE_PATTERN), totalTraffic);
            totalOperation.add(totalTrafficVo);

            //得到indexDate对应的所有用户操作数
            Long selfTraffic = logDao.getTodayOperationNumberByUsername(DateUtil.format(indexDate, DatePattern.NORM_DATE_PATTERN), username);
            TrafficVo selfTrafficVo = new TrafficVo(DateUtil.format(indexDate, DatePattern.NORM_DATE_PATTERN), selfTraffic);
            selfOperation.add(selfTrafficVo);
        } while (!DateUtil.isSameDay(today, indexDate));
        trafficOperationVo.setTotalOperation(totalOperation);
        trafficOperationVo.setSelfOperation(selfOperation);
        return trafficOperationVo;
    }

    @Override
    public void batchInsert(List<LogEntity> logEntityList) {
        logDao.batchInsert(logEntityList);
    }
}
