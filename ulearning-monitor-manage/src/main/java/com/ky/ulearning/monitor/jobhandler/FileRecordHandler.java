package com.ky.ulearning.monitor.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 文件记录有效跑批
 * 对已失效的文件索引进行删除
 *
 * @author luyuhao
 * @since 20/02/09 03:03
 */
@Slf4j
@JobHandler(value="fileRecordHandler")
@Component
public class FileRecordHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log("文件记录表扫描 开始...");

        log.info("啥事没干");

        XxlJobLogger.log("文件记录表扫描 结束...");
        return SUCCESS;
    }

}