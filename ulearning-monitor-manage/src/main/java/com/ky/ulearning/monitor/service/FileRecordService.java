package com.ky.ulearning.monitor.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import com.ky.ulearning.spi.monitor.entity.FileRecordEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 文件记录service - 接口类
 *
 * @author luyuhao
 * @since 20/02/06 17:04
 */
public interface FileRecordService {

    /**
     * 插入文件记录
     *
     * @param fileRecordDto 待插入的文件记录
     */
    @Async
    void insert(FileRecordDto fileRecordDto);

    /**
     * 分页查询文件记录
     *
     * @param fileRecordDto 筛选参数
     * @param pageParam     分页参数
     * @return 封装文件记录的分页对象
     */
    PageBean<FileRecordEntity> pageFileRecordList(FileRecordDto fileRecordDto, PageParam pageParam);

    /**
     * 根据id查询文件记录
     *
     * @param id 文件记录id
     * @return 文件记录对象
     */
    FileRecordEntity getById(Long id);

    /**
     * 删除文件记录
     *
     * @param id       文件记录id
     * @param updateBy 更新者
     */
    void delete(Long id, String updateBy);

    /**
     * 获取所有文件记录
     *
     * @return 返回文件记录集合
     */
    List<FileRecordEntity> getAll();

    /**
     * 扫描教师表
     */
    void scanTeacherTable();

    /**
     * 扫描学生表
     */
    void scanStudentTable();

    /**
     * 扫描课程试题表
     */
    void scanCourseQuestionTable();

    /**
     * 扫描教学任务实验表
     */
    void scanTeachingTaskExperimentTable();

    /**
     * 扫描教学任务通告表
     */
    void scanTeachingTaskNoticeTable();

    /**
     * 根据table和url更新tableId
     *
     * @param fileRecordEntity 参数
     */
    void updateTableIdByTableAndUrl(FileRecordEntity fileRecordEntity);

    /**
     * 更新文件大小
     *
     * @param fileRecordEntity 待更新的文件记录对象
     */
    void updateRecordSize(FileRecordEntity fileRecordEntity);

    /**
     * 扫描日志历史表
     */
    void scanLogHistoryTable();

    /**
     * 计算文件总量
     *
     * @return 文件大小总量
     */
    Long getSumFileSize();
}
