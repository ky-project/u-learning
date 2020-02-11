package com.ky.ulearning.monitor.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.LogDto;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.List;

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

    /**
     * 分页查询日志信息
     *
     * @param logDto    日志筛选条件
     * @param pageParam 分页参数
     * @return 返回封装好的分页日志对象
     */
    PageBean<LogEntity> pageLogList(LogDto logDto, PageParam pageParam);

    /**
     * 获取日志类型集合
     *
     * @return 日志类型集合
     */
    List<String> getLogType();

    /**
     * 查询小于等于startDelDate的第一个日期
     *
     * @param dateTime 待比较的时间
     * @return 返回日期
     */
    Date getFirstCreateTimeLessOrEqual(String dateTime);

    /**
     * 根据日期查询日志记录
     *
     * @param date 查询的日期,yyyy-MM-dd
     * @return 返回日志集合
     */
    List<LogEntity> getByDate(String date);

    /**
     * 根据日期删除日志
     *
     * @param date 待删除的日期
     */
    void deleteByDate(String date);
}
