package com.ky.ulearning.monitor.logging.controller;

import com.ky.ulearning.monitor.logging.service.LogService;
import com.ky.ulearning.spi.monitor.logging.entity.LogEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void add(LogEntity logEntity){
        logService.insert(logEntity);
    }

}
