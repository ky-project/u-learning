package com.ky.ulearning.monitor.jobhandler;

import cn.hutool.core.date.DateTime;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.constant.TableFileEnum;
import com.ky.ulearning.common.core.utils.DateUtil;
import com.ky.ulearning.monitor.service.FileRecordService;
import com.ky.ulearning.monitor.service.LogHistoryService;
import com.ky.ulearning.monitor.service.LogService;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import com.ky.ulearning.spi.monitor.dto.LogHistoryDto;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

/**
 * 日志表清理跑批
 *
 * @author luyuhao
 * @since 20/02/10 17:44
 */
@Slf4j
@JobHandler(value = "logHandler")
@Component
public class LogHandler extends IJobHandler {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String LOG_FILE_TYPE = "log";

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private LogService logService;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Autowired
    private LogHistoryService logHistoryService;

    @Autowired
    private FileRecordService fileRecordService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        try {
            XxlJobLogger.log(">>>>>>>>>>> 日志清理跑批 开始 <<<<<<<<<<<");
            //获取需要保留的日志天数
            int logRetentionDays = defaultConfigParameters.getLogRetentionDays();
            int maxDeleteDays = defaultConfigParameters.getLogMaxDeleteDays();
            //查询需要清理的日志范围
            Date startDelDate = DateUtil.offsetDay(new Date(), -logRetentionDays);
            Date endDelDate = logService.getFirstCreateTimeLessOrEqual(DateUtil.format(startDelDate, DATE_FORMAT));
            XxlJobLogger.log("开始清理 {} ~ {} 的日志", DateUtil.format(endDelDate, DATE_FORMAT), DateUtil.format(startDelDate, DATE_FORMAT));
            //有需要清理的日志
            if (endDelDate != null) {
                int index = 0;
                DateTime indexDate;
                do {
                    //限制每次最多清理天数，防止死循环
                    if (maxDeleteDays <= index) {
                        XxlJobLogger.log("本次最多只能清理 {} 天的日志, 已清理 {} 天的日志", maxDeleteDays, index);
                        break;
                    }
                    indexDate = DateUtil.offsetDay(startDelDate, -(index++));
                    //获取当天的所有日志
                    List<LogEntity> logList = logService.getByDate(DateUtil.format(indexDate, DATE_FORMAT));
                    //拼接日志文件内容
                    String logFileContent = readLogListToString(logList);
                    //上传文件
                    LogHistoryDto logHistoryDto = upload(logFileContent, indexDate);
                    //保存历史日志记录
                    logHistoryService.save(logHistoryDto);
                    //删除已保存的日志
                    logService.deleteByDate(DateUtil.format(indexDate, DATE_FORMAT));
                    //保存文件记录
                    saveFileRecord(logHistoryDto);
                    XxlJobLogger.log("已清理 {} 的日志", DateUtil.format(indexDate, DATE_FORMAT));
                } while (!DateUtil.isSameDay(indexDate, endDelDate));
            }
            XxlJobLogger.log(">>>>>>>>>>> 日志清理跑批 结束 <<<<<<<<<<<");
            return SUCCESS;
        } catch (Exception e) {
            XxlJobLogger.log("!!!!!!!!!!! 日志清理跑批 失败 !!!!!!!!!!!");
            XxlJobLogger.log("失败原因：{}", e.getMessage());
            log.error(e.getMessage(), e);
            return FAIL;
        }
    }

    /**
     * 保存文件记录
     */
    private void saveFileRecord(LogHistoryDto logHistoryDto) {
        FileRecordDto fileRecordDto = new FileRecordDto();
        fileRecordDto.setRecordUrl(logHistoryDto.getLogHistoryUrl());
        fileRecordDto.setRecordName(logHistoryDto.getLogHistoryName());
        fileRecordDto.setRecordSize(logHistoryDto.getLogHistorySize());
        fileRecordDto.setRecordType(LOG_FILE_TYPE);
        fileRecordDto.setRecordTable(TableFileEnum.LOG_HISTORY_TABLE.getTableName());
        fileRecordDto.setRecordTableId(logHistoryDto.getId());
        fileRecordDto.setUpdateBy("logHandler");
        fileRecordDto.setCreateBy("logHandler");
        fileRecordService.insert(fileRecordDto);
    }

    /**
     * 上传文件
     *
     * @return 返回历史日志对象
     */
    private LogHistoryDto upload(String logFileContent, DateTime indexDate) {
        //上传文件
        String logFileUrl = fastDfsClientWrapper.uploadFile(logFileContent, LOG_FILE_TYPE);
        LogHistoryDto logHistoryDto = new LogHistoryDto();
        logHistoryDto.setLogHistoryDate(DateUtil.format(indexDate, DATE_FORMAT));
        logHistoryDto.setLogHistoryName(DateUtil.format(indexDate, DATE_FORMAT) + "." + LOG_FILE_TYPE);
        logHistoryDto.setLogHistoryUrl(logFileUrl);
        logHistoryDto.setLogHistorySize((long) logFileContent.getBytes(Charset.forName("UTF-8")).length);
        logHistoryDto.setUpdateBy("logHandler");
        logHistoryDto.setCreateBy("logHandler");
        return logHistoryDto;
    }

    /**
     * 读取日志集合为字符串
     */
    private String readLogListToString(List<LogEntity> logList) {
        if (CollectionUtils.isEmpty(logList)) {
            return "";
        }
        StringBuilder res = new StringBuilder();
        for (LogEntity logEntity : logList) {
            res.append("'").append(logEntity.getId()).append("'").append("\t")
                    .append("'").append(logEntity.getLogUsername()).append("'").append("\t")
                    .append("'").append(logEntity.getLogDescription()).append("'").append("\t")
                    .append("'").append(logEntity.getLogModule()).append("'").append("\t")
                    .append("'").append(logEntity.getLogIp()).append("'").append("\t")
                    .append("'").append(logEntity.getLogType()).append("'").append("\t")
                    .append("'").append(logEntity.getLogException()).append("'").append("\t")
                    .append("'").append(logEntity.getLogParams()).append("'").append("\t")
                    .append("'").append(logEntity.getLogTime()).append("'").append("\t")
                    .append("'").append(logEntity.getLogAddress()).append("'").append("\t")
                    .append("'").append(logEntity.getValid()).append("'").append("\t")
                    .append("'").append(logEntity.getMemo()).append("'").append("\t")
                    .append("'").append(DateUtil.formatDateTime(logEntity.getCreateTime())).append("'").append("\t")
                    .append("'").append(logEntity.getCreateBy()).append("'").append("\t")
                    .append("'").append(DateUtil.formatDateTime(logEntity.getUpdateTime())).append("'").append("\t")
                    .append("'").append(logEntity.getUpdateBy()).append("'").append("\t")
                    .append("\n");
        }
        return res.toString();
    }
}
