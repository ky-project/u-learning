package com.ky.ulearning.system.sys.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.utils.TermUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.TermVo;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import com.ky.ulearning.system.auth.service.TeacherService;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import com.ky.ulearning.system.common.constants.SystemManageConfigParameters;
import com.ky.ulearning.system.sys.service.CourseService;
import com.ky.ulearning.system.sys.service.TeachingTaskService;
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
 * 教学任务管理controller
 *
 * @author luyuhao
 * @since 20/01/16 00:32
 */
@Slf4j
@RestController
@Api(tags = "教学任务管理", description = "教学任务管理接口")
@RequestMapping(value = "/teachingTask")
public class TeachingTaskController extends BaseController {

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Autowired
    private SystemManageConfigParameters systemManageConfigParameters;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Log("获取学期集合")
    @ApiOperation(value = "获取学期集合")
    @PermissionName(source = "teachingTask:getTermList", name = "获取学期集合", group = "教学任务管理")
    @GetMapping("/getTermList")
    public ResponseEntity<JsonResult<List<TermVo>>> getTermList() {
        //获取系统配置前preYears后nextYears的学期信息
        Integer preYears = systemManageConfigParameters.getPreYears();
        Integer nextYears = systemManageConfigParameters.getNextYears();
        List<TermVo> termList = TermUtil.getTermList(preYears, nextYears);

        return ResponseEntityUtil.ok(JsonResult.buildData(termList));
    }

    @Log("添加教学任务")
    @ApiOperation(value = "添加教学任务")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @PermissionName(source = "teachingTask:save", name = "添加教学任务", group = "教学任务管理")
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(TeachingTaskDto teachingTaskDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(teachingTaskDto.getTeaId()), SystemErrorCodeEnum.TEA_ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(teachingTaskDto.getCourseId()), SystemErrorCodeEnum.COURSE_ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(teachingTaskDto.getTeachingTaskAlias()), SystemErrorCodeEnum.TEACHING_TASK_ALIAS_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(teachingTaskDto.getTerm()), SystemErrorCodeEnum.TERM_CANNOT_BE_NULL)
                .doValidate().checkResult();
        //教师id是否存在
        ValidateHandler.checkParameter(teacherService.getById(teachingTaskDto.getTeaId()) == null, SystemErrorCodeEnum.TEA_ID_NOT_EXISTS);
        //课程id是否存在
        ValidateHandler.checkParameter(courseService.getById(teachingTaskDto.getCourseId()) == null, SystemErrorCodeEnum.COURSE_ID_NOT_EXISTS);
        //插入记录
        teachingTaskService.insert(teachingTaskDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
    }

    @Log("分页查询教学任务")
    @ApiOperation(value = "分页查询教学任务")
    @ApiOperationSupport(ignoreParameters = {"id"})
    @PermissionName(source = "teachingTask:pageList", name = "分页查询教学任务", group = "教学任务管理")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<TeachingTaskEntity>>> pageList(PageParam pageParam, TeachingTaskDto teachingTaskDto) {
        PageBean<TeachingTaskEntity> pageBean = teachingTaskService.pageTeachingTaskList(teachingTaskDto, setPageParam(pageParam));
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(pageBean, "查询成功"));
    }

    @Log("更新教学任务")
    @ApiOperation(value = "更新教学任务")
    @PermissionName(source = "teachingTask:update", name = "更新教学任务", group = "教学任务管理")
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(TeachingTaskDto teachingTaskDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(teachingTaskDto.getId()), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);

        //教师id是否存在
        if (StringUtil.isNotEmpty(teachingTaskDto.getTeaId())) {
            ValidateHandler.checkParameter(teacherService.getById(teachingTaskDto.getTeaId()) == null, SystemErrorCodeEnum.TEA_ID_NOT_EXISTS);
        }
        //课程id是否存在
        if (StringUtil.isNotEmpty(teachingTaskDto.getCourseId())) {
            ValidateHandler.checkParameter(courseService.getById(teachingTaskDto.getCourseId()) == null, SystemErrorCodeEnum.COURSE_ID_NOT_EXISTS);
        }

        teachingTaskDto.setUpdateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        teachingTaskService.update(teachingTaskDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("根据id查询教学任务")
    @ApiOperation(value = "根据id查询教学任务")
    @PermissionName(source = "teachingTask:getById", name = "根据id查询教学任务", group = "教学任务管理")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<TeachingTaskEntity>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);

        TeachingTaskEntity teachingTaskEntity = teachingTaskService.getById(id);
        return ResponseEntityUtil.ok(JsonResult.buildData(teachingTaskEntity));
    }

    @Log("删除教学任务")
    @ApiOperation(value = "删除教学任务")
    @PermissionName(source = "teachingTask:delete", name = "删除教学任务", group = "教学任务管理")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);

        teachingTaskService.delete(id, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

}
