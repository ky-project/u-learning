package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.system.dto.PermissionDto;
import com.ky.ulearning.spi.system.entity.PermissionEntity;
import com.ky.ulearning.system.auth.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.ky.ulearning.system.common.constants.SystemErrorCodeEnum.*;

/**
 * @author luyuhao
 * @date 19/12/07 22:56
 */
@Slf4j
@RestController
@Api(tags = "权限管理", description = "权限管理接口")
@RequestMapping(value = "/permission")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @Log("查询所有权限组")
    @ApiOperation("查询所有权限组")
    @PermissionName(source = "permission:getAllGroup", name = "查询所有权限组", group = "权限管理")
    @GetMapping("/getAllGroup")
    public ResponseEntity<JsonResult<List<String>>> getAllGroup() {
        List<String> allGroup = permissionService.getAllGroup();
        return ResponseEntityUtil.ok(JsonResult.buildData(allGroup));
    }

    @Log("查询权限")
    @ApiOperation("查询权限")
    @PermissionName(source = "permission:pageList", name = "查询权限", group = "权限管理")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<PermissionEntity>>> pageList(PageParam pageParam, PermissionDto permission) {
        PageBean<PermissionEntity> pageBean = permissionService.pagePermissionList(permission, setPageParam(pageParam));
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(pageBean, "查询成功"));
    }

    @Log("添加权限")
    @ApiOperation("添加权限")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @PermissionName(source = "permission:save", name = "添加权限", group = "权限管理")
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(PermissionDto permissionDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(permissionDto.getPermissionUrl()), PERMISSION_URL_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(permissionDto.getPermissionGroup()), PERMISSION_GROUP_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(permissionDto.getPermissionName()), PERMISSION_NAME_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(permissionDto.getPermissionSource()), PERMISSION_SOURCE_CANNOT_BE_NULL)
                .doValidate().checkResult();
        //设置创建者和更新者
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        permissionDto.setCreateBy(username);
        permissionDto.setUpdateBy(username);
        permissionService.insert(permissionDto);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(permissionDto.getId(), "添加成功"));
    }

    @Log("删除权限")
    @ApiOperation("删除权限")
    @PermissionName(source = "permission:delete", name = "删除权限", group = "权限管理")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), ID_CANNOT_BE_NULL);
        //获取更新者
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        permissionService.delete(id, username);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("更新权限")
    @ApiOperation("更新权限")
    @PermissionName(source = "permission:update", name = "更新权限", group = "权限管理")
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(PermissionDto permissionDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(permissionDto.getId()), ID_CANNOT_BE_NULL);
        //获取更新者
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        permissionDto.setUpdateBy(username);
        permissionService.update(permissionDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    /**
     * 以树的形式查询所有权限
     */
    @Log("分组查询所有权限")
    @ApiOperation(value = "分组查询所有权限", notes = "以组为单位查询所有权限")
    @PermissionName(source = "permission:groupList", name = "分组查询所有权限", group = "权限管理")
    @GetMapping(value = "/groupList")
    public ResponseEntity<JsonResult<Map<String, List<PermissionEntity>>>> groupList() {
        Map<String, List<PermissionEntity>> groupList = permissionService.groupList();

        return ResponseEntityUtil.ok(JsonResult.buildData(groupList));
    }

    @Log("查询所有权限")
    @ApiOperation(value = "查询所有权限")
    @PermissionName(source = "permission:list", name = "查询所有权限", group = "权限管理")
    @GetMapping(value = "/list")
    public ResponseEntity<JsonResult<List<PermissionEntity>>> list() {
        List<PermissionEntity> permissionEntityList = permissionService.getList();

        return ResponseEntityUtil.ok(JsonResult.buildData(permissionEntityList));
    }

    @Log("查询所有权限数组")
    @ApiOperation(value = "查询所有权限数组")
    @PermissionName(source = "permission:arrayList", name = "查询所有权限数组", group = "权限管理")
    @GetMapping(value = "/arrayList")
    public ResponseEntity<JsonResult<List<KeyLabelVo>>> arrayList() {
        List<KeyLabelVo> permissionArrayVoList = permissionService.getArrayVoList();

        return ResponseEntityUtil.ok(JsonResult.buildData(permissionArrayVoList));
    }
}
