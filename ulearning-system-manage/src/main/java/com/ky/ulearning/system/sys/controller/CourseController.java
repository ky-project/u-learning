package com.ky.ulearning.system.sys.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.CourseDto;
import com.ky.ulearning.spi.system.entity.CourseEntity;
import com.ky.ulearning.spi.system.vo.CourseVo;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import com.ky.ulearning.system.sys.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/01/13 23:48
 */
@Slf4j
@RestController
@Api(tags = "课程管理", description = "课程管理接口")
@RequestMapping(value = "/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Log("课程查询")
    @ApiOperation(value = "课程查询", notes = "分页查询，支持多条件筛选")
    @PermissionName(source = "course:pageList", name = "课程查询", group = "课程管理")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<CourseEntity>>> pageList(PageParam pageParam, CourseDto courseDto) {
        if (pageParam.getCurrentPage() != null && pageParam.getPageSize() != null) {
            pageParam.setStartIndex((pageParam.getCurrentPage() - 1) * pageParam.getPageSize());
        }
        PageBean<CourseEntity> pageBean = courseService.pageCourseList(courseDto, pageParam);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(pageBean, "查询成功"));
    }

    @Log("根据id查询课程")
    @ApiOperation(value = "根据id查询课程")
    @PermissionName(source = "course:getById", name = "根据id查询课程", group = "课程管理")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<CourseEntity>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);

        CourseEntity courseEntity = courseService.getById(id);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(courseEntity, "查询成功"));
    }

    @Log("课程添加")
    @ApiOperation(value = "课程添加")
    @ApiOperationSupport(ignoreParameters = "id")
    @PermissionName(source = "course:save", name = "课程添加", group = "课程管理")
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(CourseDto courseDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(courseDto.getCourseNumber()), SystemErrorCodeEnum.COURSE_NUMBER_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(courseDto.getCourseName()), SystemErrorCodeEnum.COURSE_NAME_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(courseDto.getCourseCredit()), SystemErrorCodeEnum.COURSE_CREDIT_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        courseDto.setUpdateBy(username);
        courseDto.setCreateBy(username);

        courseService.insert(courseDto);

        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
    }

    @Log("更新课程信息")
    @ApiOperation(value = "更新课程信息")
    @PermissionName(source = "course:update", name = "更新课程信息", group = "课程管理")
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(CourseDto courseDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(courseDto.getId()), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);

        courseDto.setUpdateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        courseService.update(courseDto);

        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("删除课程")
    @ApiOperation(value = "删除课程")
    @PermissionName(source = "course:delete", name = "删除课程", group = "课程管理")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);

        String updaterBy = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        courseService.delete(id, updaterBy);

        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("获取所有课程信息")
    @ApiOperation(value = "获取所有课程信息")
    @PermissionName(source = "course:getAll", name = "获取所有课程信息", group = "课程管理")
    @GetMapping("/getAll")
    public ResponseEntity<JsonResult<List<CourseVo>>> getAll() {
        List<CourseVo> courseVoList = courseService.getAll();
        return ResponseEntityUtil.ok(JsonResult.buildData(courseVoList));
    }
}
