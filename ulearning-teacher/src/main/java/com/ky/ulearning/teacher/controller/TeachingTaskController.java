package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
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
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.spi.teacher.dto.CourseTeachingTaskDto;
import com.ky.ulearning.teacher.common.constants.TeacherConfigParameters;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.service.CourseService;
import com.ky.ulearning.teacher.service.TeacherService;
import com.ky.ulearning.teacher.service.TeachingTaskService;
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

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherConfigParameters teacherConfigParameters;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Log("分页查询教学任务")
    @ApiOperation(value = "分页查询教学任务")
    @ApiOperationSupport(ignoreParameters = {"id", "courseId", "teaId"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<CourseTeachingTaskDto>>> pageList(PageParam pageParam,
                                                                                CourseTeachingTaskDto courseTeachingTaskDto) {
        courseTeachingTaskDto.setTeaNumber(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        PageBean<CourseTeachingTaskDto> pageBean = teachingTaskService.pageList(setPageParam(pageParam), courseTeachingTaskDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("获取学期集合")
    @ApiOperation(value = "获取学期集合")
    @GetMapping("/getTermList")
    public ResponseEntity<JsonResult<List<TermVo>>> getTermList() {
        //获取系统配置前preYears后nextYears的学期信息
        Integer preYears = teacherConfigParameters.getPreYears();
        Integer nextYears = teacherConfigParameters.getNextYears();
        List<TermVo> termList = TermUtil.getTermList(preYears, nextYears);

        return ResponseEntityUtil.ok(JsonResult.buildData(termList));
    }

    @Log("添加教学任务")
    @ApiOperation(value = "添加教学任务")
    @ApiOperationSupport(ignoreParameters = {"id", "teaId"})
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(TeachingTaskDto teachingTaskDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(teachingTaskDto.getCourseId()), TeacherErrorCodeEnum.COURSE_ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(teachingTaskDto.getTeachingTaskAlias()), TeacherErrorCodeEnum.TEACHING_TASK_ALIAS_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(teachingTaskDto.getTerm()), TeacherErrorCodeEnum.TERM_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String teaNumber = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        TeacherEntity teacherEntity = teacherService.getByTeaNumber(teaNumber);
        //教师number是否存在
        ValidateHandler.checkParameter(teacherEntity == null, TeacherErrorCodeEnum.TEA_NUMBER_NOT_EXISTS);
        //课程id是否存在
        ValidateHandler.checkParameter(courseService.getById(teachingTaskDto.getCourseId()) == null, TeacherErrorCodeEnum.COURSE_ID_NOT_EXISTS);
        //完善相关属性
        teachingTaskDto.setTeaId(teacherEntity.getId());
        teachingTaskDto.setUpdateBy(teaNumber);
        teachingTaskDto.setCreateBy(teaNumber);
        //插入记录
        teachingTaskService.insert(teachingTaskDto);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(teachingTaskDto.getId(), "添加成功"));
    }

    @Log("更新教学任务")
    @ApiOperation(value = "更新教学任务", notes = "只能更新属于自己的教学任务")
    @ApiOperationSupport(ignoreParameters = {"teaId"})
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(TeachingTaskDto teachingTaskDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(teachingTaskDto.getId()), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);

        //校验是否有操作权限
        TeacherEntity teacherEntity = teachingTaskValidUtil.checkTeachingTask(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), teachingTaskDto.getId());
        //课程id是否存在
        if (StringUtil.isNotEmpty(teachingTaskDto.getCourseId())) {
            ValidateHandler.checkParameter(courseService.getById(teachingTaskDto.getCourseId()) == null, TeacherErrorCodeEnum.COURSE_ID_NOT_EXISTS);
        }
        teachingTaskDto.setTeaId(teacherEntity.getId());
        teachingTaskDto.setUpdateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        teachingTaskService.update(teachingTaskDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("根据id查询教学任务")
    @ApiOperation(value = "根据id查询教学任务", notes = "只能查看属于自己的教学任务")
    @PostMapping("/getById")
    public ResponseEntity<JsonResult<CourseTeachingTaskDto>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        //校验是否有操作权限
        teachingTaskValidUtil.checkTeachingTask(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), id);

        CourseTeachingTaskDto courseTeachingTaskDto = teachingTaskService.getById(id);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseTeachingTaskDto));
    }
}
