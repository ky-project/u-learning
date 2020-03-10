package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.service.ExperimentResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @author luyuhao
 * @since 2020/03/09 23:45
 */
@Slf4j
@RestController
@Api(tags = "实验结果管理", description = "实验结果管理接口")
@RequestMapping(value = "/experimentResult")
public class ExperimentResultController extends BaseController {

    @Autowired
    private ExperimentResultService experimentResultService;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Log("分页查询实验结果")
    @ApiOperation(value = "分页查询实验结果", notes = "只能查看/操作已选教学任务的数据")
    @ApiOperationSupport(ignoreParameters = {"id", "experimentUrl"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<ExperimentResultDto>>> pageList(PageParam pageParam, ExperimentResultDto experimentResultDto) {
        ValidateHandler.checkNull(experimentResultDto.getExperimentId(), TeacherErrorCodeEnum.EXPERIMENT_ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        teachingTaskValidUtil.checkExperimentId(experimentResultDto.getExperimentId(), username);
        PageBean<ExperimentResultDto> experimentResultDtoList = experimentResultService.pageList(setPageParam(pageParam), experimentResultDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(experimentResultDtoList));
    }

    @Log("根据id查询实验结果")
    @ApiOperation(value = "根据id查询实验结果", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<ExperimentResultDto>> getById(Long id) {
        ValidateHandler.checkNull(id, TeacherErrorCodeEnum.EXPERIMENT_RESULT_ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        ExperimentResultDto experimentResultDto = teachingTaskValidUtil.checkExperimentResultId(id, username);
        return ResponseEntityUtil.ok(JsonResult.buildData(experimentResultDto));
    }

    @Log("批改实验结果")
    @ApiOperation(value = "批改实验结果", notes = "只能查看/操作已选教学任务的数据")
    @ApiOperationSupport(ignoreParameters = {"experimentId", "stuId", "experimentCommitState", "experimentCommitTime", "experimentResult", "experimentUrl", "experimentAttachmentName", "valid", "createBy", "updateBy", "updateTime", "createTime"})
    @PostMapping("/correctResult")
    public ResponseEntity<JsonResult> correctResult(ExperimentResultEntity experimentResultEntity) {
        ValidatorBuilder.build()
                .ofNull(experimentResultEntity.getId(), TeacherErrorCodeEnum.EXPERIMENT_RESULT_ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(experimentResultEntity.getExperimentScore()) && StringUtil.isEmpty(experimentResultEntity.getExperimentAdvice()), TeacherErrorCodeEnum.MARK_RESULT_ILLEGAL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        teachingTaskValidUtil.checkExperimentResultId(experimentResultEntity.getId(), username);
        experimentResultEntity.setUpdateBy(username);
        experimentResultService.update(experimentResultEntity);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("批改成功"));
    }

    @Log("下载附件")
    @ApiOperation(value = "下载附件", notes = "只能操作自己教学任务的实验")
    @GetMapping("/downloadAttachment")
    public ResponseEntity downloadAttachment(Long id) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //获取实验结果信息
        ExperimentResultDto experimentResultDto = teachingTaskValidUtil.checkExperimentResultId(id, username);
        //判断文件是否为空
        String attachmentUrl = experimentResultDto.getExperimentUrl();
        ValidateHandler.checkParameter(StringUtil.isEmpty(attachmentUrl), TeacherErrorCodeEnum.EXPERIMENT_ATTACHMENT_NOT_EXISTS);
        String attachmentName = StringUtil.isEmpty(experimentResultDto.getExperimentAttachmentName()) ? "未命名" : experimentResultDto.getExperimentAttachmentName();

        //下载并校验附件是否过期
        byte[] attachmentBytes = fastDfsClientWrapper.download(attachmentUrl);
        ValidateHandler.checkParameter(attachmentBytes == null, TeacherErrorCodeEnum.NOTICE_ATTACHMENT_ILLEGAL);

        //设置head
        HttpHeaders headers = new HttpHeaders();
        //文件的属性名
        headers.setContentDispositionFormData("attachment", new String(attachmentName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        //响应内容是字节流
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntityUtil.ok(headers, attachmentBytes);
    }
}
