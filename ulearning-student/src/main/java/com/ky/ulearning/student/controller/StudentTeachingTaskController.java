package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;
import com.ky.ulearning.spi.teacher.entity.StudentTeachingTaskEntity;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.common.utils.StudentTeachingTaskUtil;
import com.ky.ulearning.student.service.StudentTeachingTaskService;
import com.ky.ulearning.student.service.TeachingTaskService;
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
 * @since 20/02/21 21:31
 */
@Slf4j
@RestController
@Api(tags = "学生选课管理", description = "学生选课管理接口")
@RequestMapping(value = "/studentTeachingTask")
public class StudentTeachingTaskController extends BaseController {

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Autowired
    private StudentTeachingTaskService studentTeachingTaskService;

    @Autowired
    private StudentTeachingTaskUtil studentTeachingTaskUtil;

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

    @Log("选择教学任务")
    @ApiOperation(value = "选择教学任务", notes = "只能选择未选的教学任务")
    @PostMapping("/select")
    public ResponseEntity<JsonResult> select(Long teachingTaskId) {
        ValidatorBuilder.build()
                .ofNull(teachingTaskId, StudentErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                //验证重复选课
                .on(studentTeachingTaskUtil.selectedTeachingTask(teachingTaskId, RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class)), StudentErrorCodeEnum.STUDENT_TEACHING_TASK_SELECTED_ILLEGAL)
                .doValidate().checkResult();
        //添加选课记录
        StudentTeachingTaskEntity studentTeachingTaskEntity = new StudentTeachingTaskEntity();
        studentTeachingTaskEntity.setStuId(RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class));
        studentTeachingTaskEntity.setTeachingTaskId(teachingTaskId);
        studentTeachingTaskEntity.setUpdateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        studentTeachingTaskEntity.setCreateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        studentTeachingTaskService.insert(studentTeachingTaskEntity);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("选修成功"));
    }

    @Log("退选教学任务")
    @ApiOperation(value = "退选教学任务", notes = "只能选择未选的教学任务")
    @PostMapping("/cancelSelected")
    public ResponseEntity<JsonResult> cancelSelected(Long teachingTaskId) {
        ValidatorBuilder.build()
                .ofNull(teachingTaskId, StudentErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                //验证是否已选课
                .on(!studentTeachingTaskUtil.selectedTeachingTask(teachingTaskId, RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class)), StudentErrorCodeEnum.STUDENT_TEACHING_TASK_CANCEL_SELECTED_ILLEGAL)
                .doValidate().checkResult();
        //删除选课记录
        studentTeachingTaskService.deleteByTeachingTaskIdAndStuId(teachingTaskId,
                RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class),
                RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildMsg("退选成功"));
    }

    @Log("查询所有已选教学任务数组")
    @ApiOperation(value = "查询所有已选教学任务数组", notes = "查询学生已选的教学任务数组key-label")
    @GetMapping("/getTeachingTaskArray")
    public ResponseEntity<JsonResult<List<KeyLabelVo>>> getTeachingTaskArray() {
        List<KeyLabelVo> keyLabelVoList = studentTeachingTaskService.getTeachingTaskArray(RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class));
        return ResponseEntityUtil.ok(JsonResult.buildData(keyLabelVoList));
    }
}
