package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.entity.ActivityEntity;
import com.ky.ulearning.student.service.ActivityService;
import com.ky.ulearning.student.service.ExaminationResultService;
import com.ky.ulearning.student.service.ExperimentResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页接口
 *
 * @author luyuhao
 * @since 2020/04/05 17:21
 */
@Slf4j
@RestController
@Api(tags = "主页接口", description = "主页接口")
@RequestMapping(value = "/index")
public class IndexController extends BaseController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ExperimentResultService experimentResultService;

    @Autowired
    private ExaminationResultService examinationResultService;

    @ApiOperation(value = "分页查询教师动态")
    @GetMapping("/pageActivityList")
    public ResponseEntity<JsonResult<PageBean<ActivityEntity>>> pageActivityList(PageParam pageParam) {
        Long userId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        PageBean<ActivityEntity> pageBean = activityService.pageList(setPageParam(pageParam), userId);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @ApiOperation(value = "查询未完成的实验数量")
    @GetMapping("/getExperimentNumber")
    public ResponseEntity<JsonResult<Integer>> getExperimentNumber() {
        Long userId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        Integer result = experimentResultService.getExperimentNumber(userId);
        return ResponseEntityUtil.ok(JsonResult.buildData(result));
    }

    @ApiOperation(value = "查询未完成的测试数量")
    @GetMapping("/getExaminationNumber")
    public ResponseEntity<JsonResult<Integer>> getExaminationNumber() {
        Long userId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        Integer result = examinationResultService.getExperimentNumber(userId);
        return ResponseEntityUtil.ok(JsonResult.buildData(result));
    }
}
