package com.ky.ulearning.monitor.jobhandler;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.monitor.service.FileRecordService;
import com.ky.ulearning.spi.monitor.entity.FileRecordEntity;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文件记录有效跑批
 * 对已失效的文件索引进行删除
 *
 * @author luyuhao
 * @since 20/02/09 03:03
 */
@Slf4j
@JobHandler(value = "fileRecordHandler")
@Component
public class FileRecordHandler extends IJobHandler {

    @Autowired
    private FileRecordService fileRecordService;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        try {
            XxlJobLogger.log(">>>>>>>>>>> 文件记录跑批 开始 <<<<<<<<<<<");

            XxlJobLogger.log("1. 对所有含文件的表进行扫描");
            scanContainFileTables();

            XxlJobLogger.log("2. 删除失效文件记录，保持数据一致性");
            deleteInValidFileRecord();

            XxlJobLogger.log("3. 更新文件大小为空的文件记录，获取文件大小");
            updateRecordSizeWhenNull();

            XxlJobLogger.log("4. 对id为空table不为空的记录建立索引");
            idIndex();

            XxlJobLogger.log(">>>>>>>>>>> 文件记录跑批 结束 <<<<<<<<<<<");
            return SUCCESS;
        } catch (Exception e) {
            XxlJobLogger.log("!!!!!!!!!!! 文件记录跑批 失败 !!!!!!!!!!!");
            XxlJobLogger.log(e.getMessage());
            log.error(e.getMessage(), e);
            return FAIL;
        }
    }

    /**
     * 4. 对id为空table不为空的记录建立索引
     */
    private void idIndex() {
        List<FileRecordEntity> fileRecordList = fileRecordService.getAll();
        for (FileRecordEntity fileRecordEntity : fileRecordList) {
            //查询table不为空但id为空的记录
            if (StringUtil.isNotEmpty(fileRecordEntity.getRecordTable()) && StringUtil.isEmpty(fileRecordEntity.getRecordTableId())) {
                fileRecordService.updateTableIdByTableAndUrl(fileRecordEntity);
            }
        }
    }

    /**
     * 3. 更新文件大小为空的文件记录，获取文件大小
     */
    private void updateRecordSizeWhenNull() {
        List<FileRecordEntity> fileRecordList = fileRecordService.getAll();
        for (FileRecordEntity fileRecordEntity : fileRecordList) {
            if (StringUtil.isEmpty(fileRecordEntity.getRecordSize())) {
                FileInfo fileInfo = fastDfsClientWrapper.getFileInfo(fileRecordEntity.getRecordUrl());
                if (fileInfo == null) {
                    continue;
                }
                fileRecordEntity.setRecordSize(fileInfo.getFileSize());
                fileRecordEntity.setUpdateBy("fileRecordHandler");
                fileRecordService.updateRecordSize(fileRecordEntity);
            }
        }
    }

    /**
     * 2. 删除失效文件记录，保持数据一致性
     */
    private void deleteInValidFileRecord() {
        List<FileRecordEntity> fileRecordList = fileRecordService.getAll();
        for (FileRecordEntity fileRecordEntity : fileRecordList) {
            if (!fastDfsClientWrapper.hasFile(fileRecordEntity.getRecordUrl())) {
                fileRecordService.delete(fileRecordEntity.getId(), "fileRecordHandler");
            }
        }
    }

    /**
     * 1. 对所有含文件的表进行扫描
     */
    private void scanContainFileTables() {
        XxlJobLogger.log("扫描u_teacher表");
        fileRecordService.scanTeacherTable();
        XxlJobLogger.log("扫描u_student表");
        fileRecordService.scanStudentTable();
        XxlJobLogger.log("扫描u_course_question表");
        fileRecordService.scanCourseQuestionTable();
        XxlJobLogger.log("扫描u_teaching_task_experiment表");
        fileRecordService.scanTeachingTaskExperimentTable();
        XxlJobLogger.log("扫描u_teaching_task_notice表");
        fileRecordService.scanTeachingTaskNoticeTable();
    }
}