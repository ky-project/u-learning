package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import com.ky.ulearning.student.service.TeachingTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyuhao
 * @since 20/02/21 21:31
 */
@Slf4j
@RestController
@Api(tags = "学生选课管理", description = "学生选课管理接口")
@RequestMapping(value = "/studentTeachingTask")
public class StudentTeachingTaskController extends BaseController {

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Log("分页查询未选的教学任务")
    @ApiOperation(value = "分页查询未选的教学任务", notes = "查询学生未选的教学任务列表")
    @ApiOperationSupport(ignoreParameters = {"id", "teaId", "courseId"})
    @GetMapping("/pageNotSelectedList")
    public ResponseEntity<JsonResult<PageBean<TeachingTaskEntity>>> pageNotSelectedList(PageParam pageParam, TeachingTaskDto teachingTaskDto) {
        teachingTaskDto.setUserId(RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class));
        PageBean<TeachingTaskEntity> pageBean = teachingTaskService.pageNotSelectedList(teachingTaskDto, setPageParam(pageParam));
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(pageBean, "查询成功"));
    }

    @Log("分页查询已选的教学任务")
    @ApiOperation(value = "分页查询已选的教学任务", notes = "查询学生已选的教学任务列表")
    @ApiOperationSupport(ignoreParameters = {"id", "teaId", "courseId"})
    @GetMapping("/pageSelectedList")
    public ResponseEntity<JsonResult<PageBean<TeachingTaskEntity>>> pageSelectedList(PageParam pageParam, TeachingTaskDto teachingTaskDto) {
        teachingTaskDto.setUserId(RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class));
        PageBean<TeachingTaskEntity> pageBean = teachingTaskService.pageSelectedList(teachingTaskDto, setPageParam(pageParam));
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(pageBean, "查询成功"));
    }


}
