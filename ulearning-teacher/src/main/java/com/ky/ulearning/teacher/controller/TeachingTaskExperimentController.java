package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.constant.CommonErrorCodeEnum;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.FileUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.vo.ExperimentAttachmentVo;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.service.TeachingTaskExperimentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 实验controller
 *
 * @author luyuhao
 * @since 20/02/04 21:05
 */
@Slf4j
@RestController
@Api(tags = "实验管理", description = "实验管理接口")
@RequestMapping(value = "/teachingTaskExperiment")
public class TeachingTaskExperimentController {

    @Autowired
    private TeachingTaskExperimentService teachingTaskExperimentService;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Log("添加附件")
    @ApiOperation(value = "添加附件")
    @PostMapping("/uploadAttachment")
    public ResponseEntity<JsonResult<ExperimentAttachmentVo>> uploadAttachment(MultipartFile attachment) throws IOException {
        ValidateHandler.checkParameter(attachment == null, TeacherErrorCodeEnum.EXPERIMENT_ATTACHMENT_CANNOT_BE_NULL);
        ValidatorBuilder.build()
                //文件类型篡改校验
                .on(!FileUtil.fileTypeCheck(attachment), CommonErrorCodeEnum.FILE_TYPE_TAMPER)
                //文件类型校验
                .on(!FileUtil.fileTypeRuleCheck(attachment, FileUtil.ATTACHMENT_TYPE), CommonErrorCodeEnum.FILE_TYPE_ERROR)
                //文件大小校验
                .on(attachment.getSize() > defaultConfigParameters.getExperimentAttachmentMaxSize(), CommonErrorCodeEnum.FILE_SIZE_ERROR)
                .doValidate().checkResult();
        String attachmentUrl = fastDfsClientWrapper.uploadFile(attachment);
        return ResponseEntityUtil.ok(JsonResult.buildData(new ExperimentAttachmentVo(attachmentUrl, attachment.getOriginalFilename())));
    }

    @Log("添加实验")
    @ApiOperation("添加实验")
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(ExperimentDto experimentDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(experimentDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(experimentDto.getExperimentOrder()), TeacherErrorCodeEnum.EXPERIMENT_ORDER_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(experimentDto.getExperimentTitle()), TeacherErrorCodeEnum.EXPERIMENT_TITLE_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //教学任务id校验
        teachingTaskValidUtil.checkTeachingTask(username, experimentDto.getTeachingTaskId());
        //设置更新者信息
        experimentDto.setUpdateBy(username);
        experimentDto.setCreateBy(username);
        teachingTaskExperimentService.save(experimentDto);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(experimentDto.getId(), "添加成功"));
    }
}
