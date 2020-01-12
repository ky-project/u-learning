package com.ky.ulearning.monitor.logging.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.monitor.logging.service.LogService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.logging.dto.LogDto;
import com.ky.ulearning.spi.monitor.logging.entity.LogEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志
 *
 * @author luyuhao
 * @date 19/12/17 02:43
 */
@Api(tags = "日志管理", description = "日志管理接口")
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @ApiOperation(value = "", hidden = true)
    @PostMapping("/add")
    public void add(LogEntity logEntity) {
        logService.insert(logEntity);
    }

    @Log("日志查询")
    @ApiOperation(value = "日志查询", notes = "分页查询，支持多条件筛选")
    @ApiOperationSupport(ignoreParameters = "logTime")
    @PermissionName(source = "log:pageList", name = "日志查询", group = "日志管理")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<LogEntity>>> pageList(PageParam pageParam, LogDto logDto) {
        if (pageParam.getCurrentPage() != null && pageParam.getPageSize() != null) {
            pageParam.setStartIndex((pageParam.getCurrentPage() - 1) * pageParam.getPageSize());
        }
        PageBean<LogEntity> pageBean = logService.pageLogList(logDto, pageParam);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }
}
