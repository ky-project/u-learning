package com.ky.ulearning.monitor.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.monitor.common.constants.MonitorManageErrorCodeEnum;
import com.ky.ulearning.monitor.service.LogHistoryService;
import com.ky.ulearning.spi.monitor.entity.LogHistoryEntity;
import com.ky.ulearning.spi.monitor.vo.LogHistoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/12 17:01
 */
@Api(tags = "历史日志管理", description = "历史日志管理接口")
@RestController
@RequestMapping("/logHistory")
public class LogHistoryController {

    @Autowired
    private LogHistoryService logHistoryService;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Log(value = "查询所有历史日志", devModel = true)
    @ApiOperation(value = "查询所有历史日志")
    @PermissionName(source = "logHistory:getVoList", name = "查询所有历史日志", group = "历史日志管理")
    @GetMapping("/getVoList")
    public ResponseEntity<JsonResult<List<LogHistoryVo>>> getVoList() {
        List<LogHistoryVo> logHistoryVoList = logHistoryService.getVoList();
        return ResponseEntityUtil.ok(JsonResult.buildData(logHistoryVoList));
    }

    @Log("下载历史日志")
    @ApiOperation(value = "下载历史日志")
    @PermissionName(source = "logHistory:download", name = "下载历史日志", group = "历史日志管理")
    @GetMapping("/download")
    public ResponseEntity download(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), MonitorManageErrorCodeEnum.ID_CANNOT_BE_NULL);
        LogHistoryEntity logHistoryEntity = logHistoryService.getById(id);
        //有效校验
        ValidateHandler.checkParameter(logHistoryEntity == null, MonitorManageErrorCodeEnum.LOG_HISTORY_NOT_EXISTS);
        ValidateHandler.checkParameter(!fastDfsClientWrapper.hasFile(logHistoryEntity.getLogHistoryUrl()), MonitorManageErrorCodeEnum.FILE_ILLEGAL);
        //下载并校验附件是否过期
        byte[] logHistoryFile = fastDfsClientWrapper.download(logHistoryEntity.getLogHistoryUrl());

        //设置head
        HttpHeaders headers = new HttpHeaders();
        //文件的属性名
        headers.setContentDispositionFormData("attachment", new String(logHistoryEntity.getLogHistoryName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        //响应内容是字节流
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntityUtil.ok(headers, logHistoryFile);
    }
}
