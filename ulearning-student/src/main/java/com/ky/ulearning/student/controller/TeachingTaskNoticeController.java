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
import com.ky.ulearning.spi.teacher.dto.TeachingTaskNoticeDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.common.utils.StudentTeachingTaskUtil;
import com.ky.ulearning.student.service.TeachingTaskNoticeService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/22 16:49
 */
@Slf4j
@RestController
@Api(tags = "通告管理", description = "通告管理接口")
@RequestMapping(value = "/teachingTaskNotice")
public class TeachingTaskNoticeController extends BaseController {

    @Autowired
    private StudentTeachingTaskUtil studentTeachingTaskUtil;

    @Autowired
    private TeachingTaskNoticeService teachingTaskNoticeService;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Log(value = "分页查询通告", devModel = true)
    @ApiOperation(value = "分页查询通告", notes = "只能查看/操作已选教学任务的通告")
    @ApiOperationSupport(ignoreParameters = {"id", "noticeAttachment"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<TeachingTaskNoticeDto>>> pageList(PageParam pageParam, TeachingTaskNoticeDto teachingTaskNoticeDto) {
        ValidatorBuilder.build()
                .ofNull(teachingTaskNoticeDto.getTeachingTaskId(), StudentErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .on(!studentTeachingTaskUtil.selectedTeachingTask(teachingTaskNoticeDto.getTeachingTaskId(), RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class)), StudentErrorCodeEnum.STUDENT_TEACHING_TASK_CANCEL_SELECTED_ILLEGAL)
                .doValidate().checkResult();
        //验证是否选课
        PageBean<TeachingTaskNoticeDto> pageBean = teachingTaskNoticeService.pageList(setPageParam(pageParam), teachingTaskNoticeDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("下载附件")
    @ApiOperation(value = "下载附件", notes = "只能查看/操作已选教学任务的通告")
    @ApiOperationSupport(params = @DynamicParameters(properties = {
            @DynamicParameter(name = "id", value = "通告id"),
            @DynamicParameter(name = "attachmentName", value = "附件名")}))
    @GetMapping("/downloadAttachment")
    public ResponseEntity downloadAttachment(Long id,
                                             String attachmentName) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(id), StudentErrorCodeEnum.ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(attachmentName), StudentErrorCodeEnum.NOTICE_ATTACHMENT_CANNOT_BE_NULL)
                .doValidate().checkResult();
        //获取原通告对象并校验
        TeachingTaskNoticeEntity teachingTaskNoticeEntity = studentTeachingTaskUtil.checkNoticeId(id, RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class));

        //获取附件name和url集合
        List<String> attachmentList = StringUtil.strToList(teachingTaskNoticeEntity.getNoticeAttachment(), ",");
        List<String> attachmentNameList = StringUtil.strToList(teachingTaskNoticeEntity.getNoticeAttachmentName(), ",");

        //查询对应附件名的url
        String attachment = "";
        for (int i = 0; i < attachmentNameList.size(); i++) {
            if (attachmentName.equals(attachmentNameList.get(i))) {
                attachment = attachmentList.get(i);
                break;
            }
        }
        //检验附件名是否存在
        ValidateHandler.checkParameter(StringUtil.isEmpty(attachment), StudentErrorCodeEnum.NOTICE_ATTACHMENT_NOT_EXISTS);
        //下载并校验附件是否过期
        byte[] attachmentBytes = fastDfsClientWrapper.download(attachment);
        ValidateHandler.checkParameter(attachmentBytes == null, StudentErrorCodeEnum.NOTICE_ATTACHMENT_ILLEGAL);

        //设置head
        HttpHeaders headers = new HttpHeaders();
        //文件的属性名
        headers.setContentDispositionFormData("attachment", new String(attachmentName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        //响应内容是字节流
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntityUtil.ok(headers, attachmentBytes);
    }
}
