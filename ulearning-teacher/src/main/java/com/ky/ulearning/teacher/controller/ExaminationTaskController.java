package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.vo.ExaminationParamVo;
import com.ky.ulearning.spi.common.vo.KeyLabelVo;
import com.ky.ulearning.spi.common.vo.QuantityVo;
import com.ky.ulearning.spi.teacher.dto.ExaminationTaskDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.service.ExaminationTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试任务controller
 *
 * @author luyuhao
 * @since 20/02/13 00:58
 */
@Slf4j
@RestController
@Api(tags = "测试任务管理", description = "测试任务管理接口")
@RequestMapping(value = "/examinationTask")
public class ExaminationTaskController extends BaseController {

    @Autowired
    private ExaminationTaskService examinationTaskService;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Log("添加测试任务")
    @ApiOperationSupport(ignoreParameters = "id")
    @ApiOperation(value = "添加测试任务", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(ExaminationTaskDto examinationTaskDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(examinationTaskDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(examinationTaskDto.getExaminationName()), TeacherErrorCodeEnum.EXAMINATION_NAME_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(examinationTaskDto.getExaminationDuration()), TeacherErrorCodeEnum.EXAMINATION_DURATION_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(examinationTaskDto.getExaminationState()), TeacherErrorCodeEnum.EXAMINATION_STATE_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(examinationTaskDto.getExaminationParameters()), TeacherErrorCodeEnum.EXAMINATION_PARAMETERS_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(examinationTaskDto.getExaminationShowResult()), TeacherErrorCodeEnum.EXAMINATION_SHOW_RESULT_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        teachingTaskValidUtil.checkTeachingTask(username, examinationTaskDto.getTeachingTaskId());
        //试题参数校验
        ValidateHandler.checkParameter(!checkExaminationParam(examinationTaskDto.getExaminationParameters()), TeacherErrorCodeEnum.EXAMINATION_PARAMETERS_ILLEGAL);
        examinationTaskDto.setUpdateBy(username);
        examinationTaskDto.setCreateBy(username);
        examinationTaskService.save(examinationTaskDto);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(examinationTaskDto.getId(), "添加成功"));
    }

    @Log("分页查询测试任务")
    @ApiOperation(value = "分页查询测试任务", notes = "只能查询/操作属于自己的教学任务的数据")
    @ApiOperationSupport(ignoreParameters = "id")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<ExaminationTaskEntity>>> pageList(PageParam pageParam, ExaminationTaskDto examinationTaskDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(examinationTaskDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL);
        //权限校验
        teachingTaskValidUtil.checkTeachingTask(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), examinationTaskDto.getTeachingTaskId());
        PageBean<ExaminationTaskEntity> pageBean = examinationTaskService.pageExaminationTaskList(examinationTaskDto, setPageParam(pageParam));
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("根据id查询测试任务")
    @ApiOperation(value = "根据id查询测试任务", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<ExaminationTaskEntity>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        //权限校验
        ExaminationTaskEntity examinationTaskEntity = teachingTaskValidUtil.checkExaminationId(id, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildData(examinationTaskEntity));
    }

    @Log("更新测试任务")
    @ApiOperation(value = "更新测试任务", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(ExaminationTaskDto examinationTaskDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(examinationTaskDto.getId()), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        teachingTaskValidUtil.checkExaminationId(examinationTaskDto.getId(), username);
        //教学任务校验
        if (StringUtil.isNotEmpty(examinationTaskDto.getTeachingTaskId())) {
            teachingTaskValidUtil.checkTeachingTask(username, examinationTaskDto.getTeachingTaskId());
        }
        //题参数校验
        if (StringUtil.isNotEmpty(examinationTaskDto.getExaminationParameters())) {
            ValidateHandler.checkParameter(!checkExaminationParam(examinationTaskDto.getExaminationParameters()), TeacherErrorCodeEnum.EXAMINATION_PARAMETERS_ILLEGAL);
        }
        examinationTaskDto.setUpdateBy(username);
        examinationTaskService.update(examinationTaskDto);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(examinationTaskDto.getId(), "更新成功"));
    }

    @Log("删除测试任务")
    @ApiOperation(value = "删除测试任务", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //权限校验
        teachingTaskValidUtil.checkExaminationId(id, username);
        examinationTaskService.delete(id, username);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    /**
     * 验证试题参数是否正确
     */
    private boolean checkExaminationParam(String examinationParameters) {
        try {
            ExaminationParamVo examinationParamVo = JsonUtil.parseObject(examinationParameters, ExaminationParamVo.class);
            //验证知识点
            if (CollectionUtils.isEmpty(examinationParamVo.getQuestionKnowledges())) {
                return false;
            } else {
                for (KeyLabelVo questionKnowledge : examinationParamVo.getQuestionKnowledges()) {
                    if (StringUtil.isEmpty(questionKnowledge.getKey()) || StringUtil.isEmpty(questionKnowledge.getLabel())) {
                        return false;
                    }
                }
            }
            //验证难度
            if (StringUtil.isEmpty(examinationParamVo.getQuestionDifficulty())) {
                return false;
            }
            //验证题型
            if (CollectionUtils.isEmpty(examinationParamVo.getQuantity())) {
                return false;
            } else {
                for (QuantityVo quantityVo : examinationParamVo.getQuantity()) {
                    if (StringUtil.isEmpty(quantityVo.getAmount())
                            || StringUtil.isEmpty(quantityVo.getGrade())
                            || StringUtil.isEmpty(quantityVo.getQuestionType())) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
