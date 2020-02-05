package com.ky.ulearning.monitor.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.LogDto;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}
