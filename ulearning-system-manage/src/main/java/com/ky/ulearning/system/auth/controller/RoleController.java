package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.system.auth.service.RolePermissionService;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luyuhao
 * @date 19/12/14 16:24
 */
@Slf4j
@RestController
@Api(tags = "角色管理", description = "角色管理接口")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Log("获取角色权限集合")
    @ApiOperation(value = "获取角色权限集合")
    @GetMapping("/getPermissionListByRoleId")
    public ResponseEntity<JsonResult<List<PermissionEntity>>> getPermissionListByRoleId(List<Long> roleIdList){
        if(CollectionUtils.isEmpty(roleIdList)){
            return ResponseEntityUtil.badRequest(new JsonResult<>(SystemErrorCodeEnum.PARAMETER_EMPTY));
        }
        List<PermissionEntity> permissionList = rolePermissionService.getPermissionListByRoleId(roleIdList);
        return ResponseEntityUtil.ok(new JsonResult<>(permissionList));
    }
}
