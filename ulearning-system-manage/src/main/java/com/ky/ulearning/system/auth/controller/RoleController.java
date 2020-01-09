package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.Handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.RoleDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.system.auth.service.RolePermissionService;
import com.ky.ulearning.system.auth.service.RoleService;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luyuhao
 * @since 19/12/14 16:24
 */
@Slf4j
@RestController
@Api(tags = "角色管理", description = "角色管理接口")
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleService roleService;

    @Log("角色查询")
    @ApiOperation(value = "角色查询")
    @PermissionName(source = "role:pageList", name = "角色查询", group = "角色管理")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<RoleEntity>>> pageList(PageParam pageParam, RoleDto roleDto) {
        if (pageParam.getCurrentPage() != null && pageParam.getPageSize() != null) {
            pageParam.setStartIndex((pageParam.getCurrentPage() - 1) * pageParam.getPageSize());
        }
        PageBean<RoleEntity> pageBean = roleService.pageRoleList(roleDto, pageParam);
        return ResponseEntityUtil.ok(JsonResult.buildSuccessDateMsg(pageBean, "查询成功"));
    }

    @Log("获取角色权限集合")
    @ApiOperation(value = "获取角色权限集合")
    @ApiImplicitParam(name = "roleIdArr", value = "角色id字符串，逗号分隔")
    @PermissionName(source = "role:getPermissionListByRoleId", name = "获取角色权限集合", group = "角色管理")
    @GetMapping("/getPermissionListByRoleId")
    public ResponseEntity<JsonResult<List<PermissionEntity>>> getPermissionListByRoleId(String roleIdArr) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(roleIdArr), SystemErrorCodeEnum.PARAMETER_EMPTY);
        List<Long> roleIdList = StringUtil.strArrToLongList(roleIdArr.split(","));
        List<PermissionEntity> permissionList = rolePermissionService.getPermissionListByRoleId(roleIdList);
        return ResponseEntityUtil.ok(new JsonResult<>(permissionList));
    }


}
