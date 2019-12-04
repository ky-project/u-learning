package com.ky.ulearning.monitor.logging.dao;

import com.ky.ulearning.spi.monitor.logging.entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author luyuhao
 * @date 19/12/05 03:02
 */
@Mapper
public interface LogDao {

    /**
     * 插入记录
     *
     * @param logEntity 待插入的日志对象
     */
    void insert(LogEntity logEntity);
}
