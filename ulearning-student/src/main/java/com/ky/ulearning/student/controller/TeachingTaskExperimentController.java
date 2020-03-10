package com.ky.ulearning.student.controller;

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
import com.ky.ulearning.spi.student.dto.StudentTeachingTaskExperimentDto;
import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.common.utils.StudentTeachingTaskUtil;
import com.ky.ulearning.student.service.TeachingTaskExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * 实验管理controller
 *
 * @author luyuhao
 * @since 20/03/05 00:55
 */
@Slf4j
@RestController
@Api(tags = "实验管理", description = "实验管理接口")
@RequestMapping(value = "/teachingTaskExperiment")
public class TeachingTaskExperimentController extends BaseController {

    @Autowired
    private TeachingTaskExperimentService teachingTaskExperimentService;

    @Autowired
    private StudentTeachingTaskUtil studentTeachingTaskUtil;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Log("分页查询实验")
    @ApiOperation(value = "分页查询实验", notes = "只能查看/操作已选教学任务的数据")
    @ApiOperationSupport(ignoreParameters = {"id", "experimentAttachment"})
    @GetMapping("/pageExperimentList")
    public ResponseEntity<JsonResult<PageBean<StudentTeachingTaskExperimentDto >>> pageNotSelectedList(PageParam pageParam, ExperimentDto experimentDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(experimentDto.getTeachingTaskId()), StudentErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL);
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        studentTeachingTaskUtil.checkTeachingTaskId(experimentDto.getTeachingTaskId(), stuId);
        PageBean<StudentTeachingTaskExperimentDto > pageBean = teachingTaskExperimentService.pageList(experimentDto, setPageParam(pageParam), stuId);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(pageBean, "查询成功"));
    }

    @Log("下载附件")
    @ApiOperation(value = "下载附件", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/downloadAttachment")
    public ResponseEntity downloadAttachment(Long id) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(id), StudentErrorCodeEnum.ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        TeachingTaskExperimentDto teachingTaskExperimentDto = studentTeachingTaskUtil.checkExperimentId(id, stuId);
        //判断文件是否为空
        String attachmentUrl = teachingTaskExperimentDto.getExperimentAttachment();
        ValidateHandler.checkParameter(StringUtil.isEmpty(attachmentUrl), StudentErrorCodeEnum.EXPERIMENT_ATTACHMENT_NOT_EXISTS);
        String attachmentName = StringUtil.isEmpty(teachingTaskExperimentDto.getExperimentAttachmentName()) ? "未命名" : teachingTaskExperimentDto.getExperimentAttachmentName();

        //下载并校验附件是否过期
        byte[] attachmentBytes = fastDfsClientWrapper.download(attachmentUrl);
        ValidateHandler.checkParameter(attachmentBytes == null, StudentErrorCodeEnum.ATTACHMENT_ILLEGAL);

        //设置head
        HttpHeaders headers = new HttpHeaders();
        //文件的属性名
        headers.setContentDispositionFormData("attachment", new String(attachmentName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        //响应内容是字节流
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntityUtil.ok(headers, attachmentBytes);
    }
}
