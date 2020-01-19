package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.RoleDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.system.auth.service.RolePermissionService;
import com.ky.ulearning.system.auth.service.RoleService;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Map;

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
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(pageBean, "查询成功"));
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
        return ResponseEntityUtil.ok(JsonResult.buildData(permissionList));
    }

    @Log("添加角色")
    @ApiOperation(value = "添加角色")
    @ApiOperationSupport(ignoreParameters = "id")
    @PermissionName(source = "role:save", name = "添加角色", group = "角色管理")
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(RoleDto roleDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(roleDto.getRoleName()), SystemErrorCodeEnum.ROLE_NAME_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(roleDto.getRoleSource()), SystemErrorCodeEnum.ROLE_SOURCE_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(roleDto.getIsAdmin()), SystemErrorCodeEnum.IS_ADMIN_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        roleDto.setCreateBy(username);
        roleDto.setUpdateBy(username);
        roleService.insert(roleDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
    }

    @Log("删除角色")
    @ApiOperation(value = "删除角色")
    @PermissionName(source = "role:delete", name = "删除角色", group = "角色管理")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);
        roleService.delete(id, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("更新角色")
    @ApiOperation(value = "更新角色")
    @PermissionName(source = "role:update", name = "更新角色", group = "角色管理")
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(RoleDto roleDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(roleDto.getId()), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);

        roleDto.setUpdateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        roleService.update(roleDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("查询角色已分配权限")
    @ApiOperation(value = "查询角色已分配权限", notes = "根据角色id分组查询所拥有的所有权限")
    @PermissionName(source = "role:getAssignedPermission", name = "查询角色已分配权限", group = "角色管理")
    @GetMapping("/getAssignedPermission")
    public ResponseEntity<JsonResult<Map<String, List<PermissionEntity>>>> getAssignedPermission(Long roleId){
        Map<String, List<PermissionEntity>> permissionGroupList = rolePermissionService.getAssignedPermission(roleId);

        return ResponseEntityUtil.ok(JsonResult.buildData(permissionGroupList));
    }

    @Log("角色分配权限")
    @ApiOperation(value = "角色分配权限")
    @ApiImplicitParam(name = "permissionIds", value = "权限id字符串，逗号分隔")
    @PermissionName(source = "role:saveAssignedPermission", name = "角色分配权限", group = "角色管理")
    @PostMapping("/saveAssignedPermission")
    public ResponseEntity<JsonResult> saveAssignedPermission(Long roleId, String permissionIds){
        ValidateHandler.checkParameter(StringUtil.isEmpty(roleId), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);
        rolePermissionService.saveAssignedPermission(roleId, permissionIds, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildMsg("权限分配成功"));
    }
}
