package com.ky.ulearning.monitor.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.LogDto;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author luyuhao
 * @date 19/12/05 03:02
 */
@Mapper
@Repository
public interface LogDao {

    /**
     * 插入记录
     *
     * @param logEntity 待插入的日志对象
     */
    void insert(LogEntity logEntity);

    /**
     * 分页查询日志信息
     *
     * @param logDto    日志筛选条件
     * @param pageParam 分页参数
     * @return 日志集合
     */
    List<LogEntity> listPage(@Param("logDto") LogDto logDto, @Param("pageParam") PageParam pageParam);

    /**
     * 根据筛选条件查询总记录数
     *
     * @param logDto 筛选条件
     * @return 总记录数
     */
    Integer countListPage(@Param("logDto") LogDto logDto);

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

    /**
     * 查询当日访问用户数量
     *
     * @param today 当天日期
     * @return 当天访问人数
     */
    Long getTodayUserNumber(String today);

    /**
     * 查询前topNumber条日志
     *
     * @param topNumber 查询数量
     * @return 返回日志对象集合
     */
    List<LogEntity> getLogTop(Integer topNumber);

    /**
     * 查询当日访问用户操作数
     *
     * @param today 当天日期
     * @return 当天访问操作数
     */
    Long getTodayOperationNumber(String today);

    /**
     * 查询当日指定访问用户操作数
     *
     * @param today    当天日期
     * @param username 用户
     * @return 当天该用户访问操作数
     */
    Long getTodayOperationNumberByUsername(@Param("today") String today, @Param("username") String username);

    /**
     * 批量插入记录
     *
     * @param logEntityList 待插入的日志对象
     */
    void batchInsert(@Param("logEntityList") List<LogEntity> logEntityList);
}
