package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.spi.system.dto.PermissionInsertDto;
import com.ky.ulearning.system.auth.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyuhao
 * @date 19/12/07 22:56
 */
@Slf4j
@RestController
@Api(tags = "权限管理", description = "权限管理接口")
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Log("添加权限")
    @ApiOperation("添加权限")
    @PostMapping("/add")
    public ResponseEntity<JsonResult> add(@Validated PermissionInsertDto permissionInsertDto) {
        permissionService.insert(permissionInsertDto);
        return ResponseEntityUtil.ok(new JsonResult<>());
    }
}
