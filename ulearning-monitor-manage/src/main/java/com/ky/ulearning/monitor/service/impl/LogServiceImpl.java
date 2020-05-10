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
    public TrafficVo getTrafficByDate(Date today) {
        String todayStr = DateUtil.format(today, DatePattern.NORM_DATE_PATTERN);
        Long todayUserNumber = logDao.getTodayUserNumber(todayStr);
        return new TrafficVo(todayStr, todayUserNumber);
    }

    @Override
    public List<LogEntity> getLogTop(Integer topNumber) {
        return logDao.getLogTop(topNumber);
    }

    @Override
    public TrafficOperationVo getDaysOperation(Integer days, String username) {
        TrafficOperationVo trafficOperationVo = new TrafficOperationVo();
        List<TrafficVo> totalOperation = logDao.getOperationNumberLimitDays(days);
        List<TrafficVo> selfOperation = logDao.getTodayOperationNumberByUsername(days, username);

        //补充缺少的统计记录
        if (totalOperation.size() != days) {
            addLostDate(days, totalOperation);
        }
        //补充缺少的统计记录
        if (selfOperation.size() != days) {
            addLostDate(days, selfOperation);
        }

        trafficOperationVo.setTotalOperation(totalOperation);
        trafficOperationVo.setSelfOperation(selfOperation);
        return trafficOperationVo;
    }

    /**
     * 补充缺少的统计记录
     *
     * @param days 天数
     * @author luyuhao
     * @date 20/05/10 13:35
     */
    private void addLostDate(Integer days, List<TrafficVo> list) {
        for (int i = days - 1, j = 0; i >= 0; i--, j++) {
            DateTime dateTime = DateUtil.offsetDay(new Date(), -i);
            String format = DateUtil.format(dateTime, "yyyy-MM-dd");
            if(j >= list.size() || ! list.get(j).getDate().equals(format)){
                TrafficVo trafficVo = new TrafficVo();
                trafficVo.setDate(format);
                trafficVo.setNumber(0L);
                list.add(j, trafficVo);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void batchInsert(List<LogEntity> logEntityList) {
        logDao.batchInsert(logEntityList);
    }
}
