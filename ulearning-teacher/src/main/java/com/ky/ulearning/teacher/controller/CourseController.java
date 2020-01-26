package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.spi.system.vo.CourseVo;
import com.ky.ulearning.teacher.service.CourseService;
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
 * @author luyuhao
 * @since 20/01/26 21:30
 */
@Slf4j
@RestController
@Api(tags = "课程管理", description = "课程管理接口")
@RequestMapping(value = "/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    @Log("教师-获取所有课程信息")
    @ApiOperation(value = "获取所有课程信息")
    @GetMapping("/getAll")
    public ResponseEntity<JsonResult<List<CourseVo>>> getAll() {
        List<CourseVo> courseVoList = courseService.getAll();
        return ResponseEntityUtil.ok(JsonResult.buildData(courseVoList));
    }
}
