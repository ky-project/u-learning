package com.ky.ulearning.monitor.dao;

import com.ky.ulearning.spi.monitor.dto.LogHistoryDto;
import com.ky.ulearning.spi.monitor.entity.LogHistoryEntity;
import com.ky.ulearning.spi.monitor.vo.LogHistoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 历史日志dao
 *
 * @author luyuhao
 * @since 2020/02/10 20:38
 */
@Mapper
@Repository
public interface LogHistoryDao {
    /**
     * 新增历史日志记录
     *
     * @param logHistoryDto 待插入的历史日志对象
     */
    void insert(LogHistoryDto logHistoryDto);

    /**
     * 根据id查询历史日志
     *
     * @param id 历史日志id
     * @return 返回历史日志对象
     */
    LogHistoryEntity getById(Long id);

    /**
     * 查询所有历史日志
     *
     * @return 历史日志vo集合
     */
    List<LogHistoryVo> getVoList();
}