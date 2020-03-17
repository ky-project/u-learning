package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.teacher.dto.ExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.common.utils.StudentTeachingTaskUtil;
import com.ky.ulearning.student.service.ExaminationTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luyuhao
 * @since 20/03/07 23:55
 */
@Slf4j
@RestController
@Api(tags = "测试任务管理", description = "测试任务管理接口")
@RequestMapping(value = "/examinationTask")
public class ExaminationTaskController extends BaseController {

    @Autowired
    private ExaminationTaskService examinationTaskService;

    @Autowired
    private StudentTeachingTaskUtil studentTeachingTaskUtil;

    @Log("分页查询测试任务")
    @ApiOperation(value = "分页查询测试任务", notes = "只能查询/操作属于自己的教学任务的数据")
    @ApiOperationSupport(ignoreParameters = {"id", "examinationParameters"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<ExaminationTaskDto>>> pageList(PageParam pageParam, ExaminationTaskDto examinationTaskDto) {
        ValidateHandler.checkNull(examinationTaskDto.getTeachingTaskId(), StudentErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL);
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        //权限校验
        studentTeachingTaskUtil.checkTeachingTaskId(examinationTaskDto.getTeachingTaskId(), stuId);

        examinationTaskDto.setUserId(stuId);
        PageBean<ExaminationTaskDto> pageBean = examinationTaskService.pageExaminationTaskList(examinationTaskDto, setPageParam(pageParam));
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("根据id查询测试任务")
    @ApiOperation(value = "根据id查询测试任务", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<ExaminationTaskEntity>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), StudentErrorCodeEnum.ID_CANNOT_BE_NULL);
        //权限校验
        ExaminationTaskEntity examinationTaskEntity = studentTeachingTaskUtil.checkExaminationId(id, RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class));
        return ResponseEntityUtil.ok(JsonResult.buildData(examinationTaskEntity));
    }

    @Log("查询所有测试任务数组")
    @ApiOperation(value = "查询所有测试任务数组", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/getExaminationTaskArr")
    public ResponseEntity<JsonResult<List<KeyLabelVo>>> getExaminationTaskArr(Long teachingTaskId) {
        ValidateHandler.checkNull(teachingTaskId, StudentErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL);
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        //权限校验
        studentTeachingTaskUtil.checkTeachingTaskId(teachingTaskId, stuId);
        List<KeyLabelVo> keyLabelVoList = examinationTaskService.getExaminationTaskArr(teachingTaskId);
        return ResponseEntityUtil.ok(JsonResult.buildData(keyLabelVoList));
    }
}
