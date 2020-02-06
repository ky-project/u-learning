package com.ky.ulearning.monitor.controller;

import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.monitor.service.FileRecordService;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件管理controller
 *
 * @author luyuhao
 * @since 20/02/06 17:06
 */
@Api(tags = "文件管理", description = "文件管理接口")
@RestController
@RequestMapping("/fileRecord")
public class FileRecordController extends BaseController {

    @Autowired
    private FileRecordService fileRecordService;

    @ApiOperation(value = "", hidden = true)
    @PostMapping("/add")
    public void add(FileRecordDto fileRecordDto) {
        fileRecordService.insert(fileRecordDto);
    }
}
