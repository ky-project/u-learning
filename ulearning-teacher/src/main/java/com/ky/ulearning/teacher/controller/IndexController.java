package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.entity.ActivityEntity;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;
import com.ky.ulearning.teacher.service.ActivityService;
import com.ky.ulearning.teacher.service.ExaminationTaskService;
import com.ky.ulearning.teacher.service.TeachingTaskExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页controller
 *
 * @author luyuhao
 * @since 2020/04/04 19:44
 */
@Slf4j
@RestController
@Api(tags = "主页相关接口", description = "主页相关接口")
@RequestMapping(value = "/index")
public class IndexController extends BaseController {

    @Autowired
    private TeachingTaskExperimentService teachingTaskExperimentService;

    @Autowired
    private ExaminationTaskService examinationTaskService;

    @Autowired
    private ActivityService activityService;

    @ApiOperation(value = "查询实验数量", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/getExperimentNumber")
    public ResponseEntity<JsonResult<Integer>> getExperimentNumber() {
        Long userId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        Integer result = teachingTaskExperimentService.getExperimentNumber(userId);
        return ResponseEntityUtil.ok(JsonResult.buildData(result));
    }

    @ApiOperation(value = "查询测试数量", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/getExaminationNumber")
    public ResponseEntity<JsonResult<Integer>> getExaminationNumber() {
        Long userId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        Integer result = examinationTaskService.getExaminationNumber(userId);
        return ResponseEntityUtil.ok(JsonResult.buildData(result));
    }

    @ApiOperation(value = "分页查询学生动态")
    @GetMapping("/pageActivityList")
    public ResponseEntity<JsonResult<PageBean<ActivityEntity>>> pageActivityList(PageParam pageParam) {
        Long userId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        PageBean<ActivityEntity> pageBean = activityService.pageList(setPageParam(pageParam), userId);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }
}
