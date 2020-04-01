package com.ky.ulearning.monitor.controller;

import com.alibaba.druid.stat.DruidStatService;
import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.constant.CommonConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.monitor.remoting.StudentRemoting;
import com.ky.ulearning.monitor.remoting.SystemManageRemoting;
import com.ky.ulearning.monitor.remoting.TeacherRemoting;
import com.ky.ulearning.monitor.common.constants.MonitorManageErrorCodeEnum;
import com.ky.ulearning.spi.common.vo.DruidWebUriVo;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * druid监控数据处理器
 * need permission -> monitor:druidStat /monitor-manage/druidStat/*
 *
 * @author luyuhao
 * @since 2020/3/14 10:29
 */
@Slf4j
@RestController
@Api(tags = "druid监控数据管理", description = "druid监控数据管理接口")
@RequestMapping(value = "/druidStat")
public class DruidStatController {

    /**
     * 过滤不显示的接口统计
     */
    private static final String FILTER_URL = "/actuator";

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private SystemManageRemoting systemManageRemoting;

    @Autowired
    private TeacherRemoting teacherRemoting;

    @Autowired
    private StudentRemoting studentRemoting;

    private DruidStatService druidStatService = DruidStatService.getInstance();

    @Log(value = "查询系统模块映射", devModel = true)
    @ApiOperation(value = "查询系统模块映射")
    @GetMapping("/getSystemModules")
    public ResponseEntity<JsonResult<List<KeyLabelVo>>> getSystemModules() {
        Map<Integer, String> moduleMap = defaultConfigParameters.getModuleMap();
        List<KeyLabelVo> res = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : moduleMap.entrySet()) {
            KeyLabelVo keyLabelVo = new KeyLabelVo();
            keyLabelVo.setKey(String.valueOf(entry.getKey()));
            keyLabelVo.setLabel(entry.getValue());
            res.add(keyLabelVo);
        }
        return ResponseEntityUtil.ok(JsonResult.buildData(res));
    }

    @Log(value = "接口统计查询", devModel = true)
    @ApiOperation(value = "接口统计查询")
    @GetMapping("/apiStat")
    public ResponseEntity<JsonResult<List<DruidWebUriVo>>> getApiStat(Integer module) {
        ValidateHandler.checkNull(module, MonitorManageErrorCodeEnum.MODULE_CANNOT_BE_NULL);
        List<DruidWebUriVo> druidWebUriVoList = new ArrayList<>();
        switch (module) {
            //后台管理系统
            case 1:
                druidWebUriVoList = Optional.ofNullable(systemManageRemoting.getApiStat())
                        .map(JsonResult::getData)
                        .orElse(Collections.emptyList());
                break;
            //监控系统
            case 2:
                druidWebUriVoList = getSelfApiStat();
                break;
            //教师端
            case 3:
                druidWebUriVoList = Optional.ofNullable(teacherRemoting.getApiStat())
                        .map(JsonResult::getData)
                        .orElse(Collections.emptyList());
                break;
            //学生端
            case 4:
                druidWebUriVoList = Optional.ofNullable(studentRemoting.getApiStat())
                        .map(JsonResult::getData)
                        .orElse(Collections.emptyList());
                break;
            default:
                break;
        }

        //过滤/actuator接口统计信息
        for (int i = 0; i < druidWebUriVoList.size(); i++) {
            DruidWebUriVo druidWebUriVo = druidWebUriVoList.get(i);
            if (druidWebUriVo.getUri().contains(FILTER_URL)) {
                druidWebUriVoList.remove(i--);
            }
        }
        return ResponseEntityUtil.ok(JsonResult.buildData(druidWebUriVoList));
    }

    private List<DruidWebUriVo> getSelfApiStat() {
        String service = druidStatService.service(CommonConstant.DRUID_STAT_WEB_URI);
        Map res = JsonUtil.parseObject(service, Map.class);
        Object contentJson = res.get("Content");
        return JsonUtil.parseArray(contentJson.toString(), DruidWebUriVo.class);
    }
}
