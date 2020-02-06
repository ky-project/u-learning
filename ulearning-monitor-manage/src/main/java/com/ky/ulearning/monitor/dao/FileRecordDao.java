package com.ky.ulearning.monitor.dao;

import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import com.ky.ulearning.spi.monitor.entity.FileRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件记录dao
 *
 * @author luyuhao
 * @since 2020/02/06 16:56
 */
@Mapper
@Repository
public interface FileRecordDao {

    /**
     * 插入文件记录
     *
     * @param fileRecordDto 待插入的文件记录对象
     */
    void insert(FileRecordDto fileRecordDto);

    /**
     * 根据id查询文件记录
     *
     * @param id 文件记录id
     * @return 文件记录对象
     */
    FileRecordEntity getById(Long id);

    /**
     * 根据id更新记录有效位
     *
     * @param id       文件记录id
     * @param updateBy 更新者
     * @param valid    有效位
     */
    void updateValidById(@Param("id") Long id,
                         @Param("updateBy") String updateBy,
                         @Param("valid") Integer valid);

    /**
     * 分页查询文件记录
     *
     * @param fileRecordDto 筛选条件
     * @param pageParam     分页参数
     * @return 文件记录集合
     */
    List<FileRecordEntity> listPage(@Param("fileRecordDto") FileRecordDto fileRecordDto,
                                    @Param("pageParam") PageParam pageParam);

    /**
     * 分页查询文件记录 - 总记录数
     *
     * @param fileRecordDto 筛选条件
     * @return 总记录数
     */
    Integer countListPage(@Param("fileRecordDto") FileRecordDto fileRecordDto);
}