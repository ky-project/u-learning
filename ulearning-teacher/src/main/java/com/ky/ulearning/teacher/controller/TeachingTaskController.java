package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;
import com.ky.ulearning.teacher.service.TeachingTaskService;
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
 * 教学任务controller
 *
 * @author luyuhao
 * @since 20/01/26 15:55
 */
@Slf4j
@RestController
@Api(tags = "教学任务管理", description = "教学任务管理接口")
@RequestMapping(value = "/teachingTask")
public class TeachingTaskController extends BaseController {

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Log("教师-分页查询教学任务")
    @ApiOperation(value = "分页查询教学任务")
    @ApiOperationSupport(ignoreParameters = {"id", "courseId", "teaId"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<CourseTeachingTaskDto>>> pageList(PageParam pageParam,
                                                                                CourseTeachingTaskDto courseTeachingTaskDto) {
        courseTeachingTaskDto.setTeaNumber(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        PageBean<CourseTeachingTaskDto> pageBean = teachingTaskService.pageList(setPageParam(pageParam), courseTeachingTaskDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

}
