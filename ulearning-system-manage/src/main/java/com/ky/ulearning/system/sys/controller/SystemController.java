package com.ky.ulearning.system.sys.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.RedisClientWrapper;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.spi.system.dto.PermissionDto;
import com.ky.ulearning.system.auth.service.PermissionService;
import com.ky.ulearning.system.common.constants.SystemManageConfigParameters;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Map;

/**
 * @author luyuhao
 * @since 20/02/06 23:18
 */
@Slf4j
@RestController
@Api(tags = "系统管理", description = "系统管理接口")
@RequestMapping(value = "/system")
public class SystemController extends BaseController {

    @Autowired
    private RedisClientWrapper redisClientWrapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private SystemManageConfigParameters systemManageConfigParameters;

    @Log("清空redis缓存")
    @ApiOperation(value = "清空redis缓存", notes = "当直接修改数据库或其他缓存问题时执行")
    @PermissionName(source = "system:clearRedis", name = "清空redis缓存", group = "系统管理")
    @GetMapping("/clearRedis")
    public ResponseEntity<JsonResult> clearRedis() {
        redisClientWrapper.clear();
        return ResponseEntityUtil.ok(JsonResult.buildMsg("清空成功"));
    }

    @Log("加载系统权限")
    @ApiOperation(value = "加载系统权限", notes = "加载系统所有权限至数据库")
    @PermissionName(source = "system:reload", name = "加载系统权限", group = "系统管理")
    @GetMapping(value = "/reloadPermission")
    public ResponseEntity<JsonResult> reloadPermission() {
        log.debug("重新加载系统权限ing...");
        //0.查询出所有的权限表达式和url，然后对比，如果已经存在，跳过，不存在添加
        List<String> sources = permissionService.getSources();
        List<String> urls = permissionService.getAllUrl();
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
                if (urls.contains(systemManageConfigParameters.getContextPath() + path)) {
                    continue;
                }
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
