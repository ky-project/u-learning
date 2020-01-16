package com.ky.ulearning.system.sys.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.TermUtil;
import com.ky.ulearning.spi.common.vo.TermVo;
import com.ky.ulearning.system.common.constants.SystemManageConfigParameters;
import com.ky.ulearning.system.sys.service.TeachingTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 教学任务管理controller
 *
 * @author luyuhao
 * @since 20/01/16 00:32
 */
@Slf4j
@RestController
@Api(tags = "教学任务管理", description = "教学任务管理接口")
@RequestMapping(value = "/teachingTask")
public class TeachingTaskController {

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Autowired
    private SystemManageConfigParameters systemManageConfigParameters;

    @Log("获取学期集合")
    @ApiOperation(value = "获取学期集合")
    @PermissionName(source = "course:getTermList", name = "获取学期集合", group = "课程管理")
    @GetMapping("/getTermList")
    public ResponseEntity<JsonResult<List<TermVo>>> getTermList() {
        //获取系统配置前preYears后nextYears的学期信息
        Integer preYears = systemManageConfigParameters.getPreYears();
        Integer nextYears = systemManageConfigParameters.getNextYears();
        List<TermVo> termList = TermUtil.getTermList(preYears, nextYears);

        return ResponseEntityUtil.ok(JsonResult.buildData(termList));
    }
}
