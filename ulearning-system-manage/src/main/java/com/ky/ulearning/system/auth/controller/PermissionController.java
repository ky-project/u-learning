package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.Handler.ValidateHandler;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.validator.ValidatorHolder;
import com.ky.ulearning.spi.system.dto.PermissionDto;
import com.ky.ulearning.system.auth.service.PermissionService;
import com.ky.ulearning.system.common.constants.SystemManageConfigParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.spring.web.json.Json;

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
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private SystemManageConfigParameters systemManageConfigParameters;

    @Log("添加权限")
    @ApiOperation("添加权限")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @PermissionName(source = "permission:save", name = "添加权限", group = "权限管理")
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(PermissionDto permissionDto) {
        ValidatorHolder validatorHolder = ValidatorBuilder.build()
                .on(StringUtil.isEmpty(permissionDto.getPermissionUrl()), PERMISSION_URL_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(permissionDto.getPermissionGroup()), PERMISSION_GROUP_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(permissionDto.getPermissionName()), PERMISSION_NAME_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(permissionDto.getPermissionSource()), PERMISSION_SOURCE_CANNOT_BE_NULL)
                .doValidate();
        //结果校验
        ValidateHandler.checkValidator(validatorHolder);
        //设置创建者和更新者
        String username = RequestHolderUtil.getHeaderByName(MicroConstant.USERNAME);
        permissionDto.setCreateBy(username);
        permissionDto.setUpdateBy(username);
        permissionService.insert(permissionDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
    }

    @Log("删除权限")
    @ApiOperation("删除权限")
    @PermissionName(source = "permission:delete", name = "删除权限", group = "权限管理")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id){
        if(StringUtil.isEmpty(id)){
            return ResponseEntityUtil.badRequest(new JsonResult<>(ID_CANNOT_BE_NULL));
        }
        //获取更新者
        String username = RequestHolderUtil.getHeaderByName(MicroConstant.USERNAME);
        permissionService.delete(id, username);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("更新权限")
    @ApiOperation("更新权限")
    @PermissionName(source = "permission:update", name = "更新权限", group = "权限管理")
    @GetMapping("/update")
    public ResponseEntity<JsonResult> update(PermissionDto permissionDto){
        if(StringUtil.isEmpty(permissionDto.getId())){
            return ResponseEntityUtil.badRequest(new JsonResult<>(ID_CANNOT_BE_NULL));
        }
        //获取更新者
        String username = RequestHolderUtil.getHeaderByName(MicroConstant.USERNAME);
        permissionDto.setUpdateBy(username);
        permissionService.update(permissionDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    /**
     * 加载程序中的所有权限到数据库中
     *
     * @return 返回json数据
     */
    @Log("权限重载")
    @ApiOperation(value = "权限重载", notes = "加载系统所有权限至数据库")
    @PermissionName(source = "permission:reload", name = "权限重载", group = "权限管理")
    @GetMapping(value = "/reload")
    public ResponseEntity<JsonResult> reloadPermission() {
        log.debug("重新加载系统权限ing...");
        //0.查询出所有的权限表达式，然后对比，如果已经存在，跳过，不存在添加
        List<String> sources = permissionService.getSources();
        //1. 获取Controller中所有待遇@requestMapper标签的方法
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> methodEntry : handlerMethodMap.entrySet()) {
            HandlerMethod method = methodEntry.getValue();
            //2. 遍历所有方法，判断当前方法是否贴有@RequiresPermissions标签
            PermissionName annotation = method.getMethodAnnotation(PermissionName.class);
            if (annotation != null) {
                //3. 如果有，解析得到权限表达式，封装成Permission对象保存到Permission表中
                //权限表达式
                String source = annotation.source();
                if (sources.contains(source)) {
                    continue;
                }
                String path = methodEntry.getKey().getPatternsCondition().toString();
                path = path.substring(1, path.length() - 1);
                log.info(systemManageConfigParameters.getContextPath() + path);
                PermissionDto permission = new PermissionDto();
                //设置权限名称、权限组和资源名
                permission.setPermissionName(annotation.name());
                permission.setPermissionSource(source);
                permission.setPermissionGroup(annotation.group());
                permission.setPermissionUrl(systemManageConfigParameters.getContextPath() + path);
                permission.setCreateBy("system");
                permission.setUpdateBy("system");
                //保存到数据库
                permissionService.insert(permission);
                sources.add(source);
            }
        }
        log.debug("重新加载系统权限...完毕");
        return ResponseEntityUtil.ok(JsonResult.buildMsg("权限加载完成"));
    }
}
