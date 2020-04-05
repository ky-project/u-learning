package com.ky.ulearning.monitor.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.DateUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.monitor.common.constants.MonitorManageErrorCodeEnum;
import com.ky.ulearning.monitor.service.LogService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.LogDto;
import com.ky.ulearning.spi.monitor.entity.LogEntity;
import com.ky.ulearning.spi.monitor.vo.TrafficOperationVo;
import com.ky.ulearning.spi.monitor.vo.TrafficVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 日志
 *
 * @author luyuhao
 * @date 19/12/17 02:43
 */
@Api(tags = "日志管理", description = "日志管理接口")
@RestController
@RequestMapping("/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @ApiOperation(value = "", hidden = true)
    @PostMapping("/add")
    public void add(LogEntity logEntity) {
        logService.insert(logEntity);
    }

    @Log(value = "日志查询", devModel = true)
    @ApiOperation(value = "日志查询", notes = "分页查询，支持多条件筛选")
    @ApiOperationSupport(ignoreParameters = "logTime")
    @PermissionName(source = "log:pageList", name = "日志查询", group = "日志管理")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<LogEntity>>> pageList(PageParam pageParam, LogDto logDto) {
        PageBean<LogEntity> pageBean = logService.pageLogList(logDto, setPageParam(pageParam));
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @ApiOperation(value = "日志类型查询")
    @PermissionName(source = "log:logTypeList", name = "日志类型查询", group = "日志管理")
    @GetMapping("/logTypeList")
    public ResponseEntity<JsonResult<List<String>>> logTypeList() {
        List<String> logTypeList = logService.getLogType();
        return ResponseEntityUtil.ok(JsonResult.buildData(logTypeList));
    }

    @ApiOperation(value = "查询当天访问量")
    @PermissionName(source = "log:getTodayTraffic", name = "查询当天访问量", group = "日志管理")
    @GetMapping("/getTodayTraffic")
    public ResponseEntity<JsonResult<TrafficVo>> getTodayTraffic() {
        TrafficVo trafficVo = logService.getTrafficByDate(new Date());
        return ResponseEntityUtil.ok(JsonResult.buildData(trafficVo));
    }

    @ApiOperation(value = "查询前n条日志")
    @PermissionName(source = "log:getTop", name = "查询前n条日志", group = "日志管理")
    @GetMapping("/getTop")
    public ResponseEntity<JsonResult<List<LogEntity>>> getTopTwentyLog(Integer topNumber) {
        List<LogEntity> logEntityList = logService.getLogTop(topNumber);
        return ResponseEntityUtil.ok(JsonResult.buildData(logEntityList));
    }

    @ApiOperation(value = "查询近n天的操作量")
    @PermissionName(source = "log:getDaysOperation", name = "查询近n天的访问量", group = "日志管理")
    @GetMapping("/getDaysOperation")
    public ResponseEntity<JsonResult<TrafficOperationVo>> getDaysOperation(Integer days) {
        ValidateHandler.checkParameter(days <= 0, MonitorManageErrorCodeEnum.TRAFFIC_DAYS_ERROR);
        TrafficOperationVo trafficOperationVo = logService.getDaysOperation(days, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildData(trafficOperationVo));
    }
}
