package com.ky.ulearning.student.controller;

import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
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
import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.spi.student.entity.ExperimentResultEntity;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.spi.teacher.entity.ExaminationTaskEntity;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.common.utils.StudentTeachingTaskUtil;
import com.ky.ulearning.student.service.ExperimentResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.validation.Validator;
import java.io.IOException;
import java.util.Date;

/**
 * @author luyuhao
 * @since 20/03/06 01:52
 */
@Slf4j
@RestController
@Api(tags = "实验结果管理", description = "教学资源管理接口")
@RequestMapping(value = "/experimentResult")
public class ExperimentResultController extends BaseController {

    @Autowired
    private ExperimentResultService experimentResultService;

    @Autowired
    private StudentTeachingTaskUtil studentTeachingTaskUtil;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Log("提交实验结果")
    @ApiOperation(value = "提交实验结果", notes = "只能查看/操作已选教学任务的数据")
    @ApiOperationSupport(ignoreParameters = {"id", "experimentUrl", "experimentScore", "experimentAdvice", "experimentAttachmentName", "experimentShared"})
    @PostMapping("/submitExperimentResult")
    public ResponseEntity<JsonResult> submitExperimentResult(MultipartFile file, ExperimentResultDto experimentResultDto) throws IOException {
        ValidateHandler.checkNull(experimentResultDto.getExperimentId(), StudentErrorCodeEnum.EXPERIMENT_ID_CANNOT_BE_NULL);
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        String stuName = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //验证权限
        studentTeachingTaskUtil.checkExperimentId(experimentResultDto.getExperimentId(), stuId);
        //查询是否已提交
        ExperimentResultEntity experimentResultEntity = experimentResultService.getByExperimentIdAndStuId(experimentResultDto.getExperimentId(), stuId);
        ValidateHandler.checkParameter(StringUtil.isNotEmpty(experimentResultEntity), StudentErrorCodeEnum.EXPERIMENT_RESULT_ILLEGAL);
        ValidateHandler.checkParameter(StringUtil.isEmpty(file) && StringUtil.isEmpty(experimentResultDto.getExperimentResult()), StudentErrorCodeEnum.EXPERIMENT_RESULT_CANNOT_BE_NULL);
        //创建对象
        experimentResultDto.setCreateBy(stuName);
        experimentResultDto.setUpdateBy(stuName);
        experimentResultDto.setStuId(stuId);
        experimentResultDto.setExperimentCommitState(true);
        experimentResultDto.setExperimentCommitTime(new Date());
        //上传附件
        if(StringUtil.isNotEmpty(file)){
            ValidatorBuilder.build()
                    .on(! FileUtil.fileTypeRuleCheck(file, FileUtil.ATTACHMENT_TYPE), CommonErrorCodeEnum.FILE_TYPE_ERROR)
                    .on(! FileUtil.fileTypeCheck(file), CommonErrorCodeEnum.FILE_TYPE_TAMPER)
                    //文件大小校验
                    .on(file.getSize() > defaultConfigParameters.getExperimentAttachmentMaxSize(), CommonErrorCodeEnum.FILE_SIZE_ERROR)
                    .doValidate().checkResult();
            String fileUrl = fastDfsClientWrapper.uploadFile(file);
            experimentResultDto.setExperimentUrl(fileUrl);
            experimentResultDto.setExperimentAttachmentName(file.getOriginalFilename());
        }
        experimentResultService.add(experimentResultDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("提交成功"));
    }

    @Log("根据id查询实验结果")
    @ApiOperation(value = "根据id查询实验结果", notes = "只能查看/操作已选教学任务的数据")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<ExperimentResultDto>> getById(Long id){
        ValidateHandler.checkNull(id, StudentErrorCodeEnum.EXPERIMENT_ID_CANNOT_BE_NULL);
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        //验证权限
        ExperimentResultEntity experimentResultEntity = studentTeachingTaskUtil.checkExperimentResultId(id, stuId);
        ExperimentResultDto experimentResultDto = new ExperimentResultDto();
        BeanUtils.copyProperties(experimentResultEntity, experimentResultDto);
        if(StringUtil.isNotEmpty(experimentResultDto.getExperimentUrl())){
            FileInfo fileInfo = fastDfsClientWrapper.getFileInfo(experimentResultDto.getExperimentUrl());
            experimentResultDto.setExperimentAttachmentSize(fileInfo.getFileSize());
        }
        return ResponseEntityUtil.ok(JsonResult.buildData(experimentResultDto));
    }
}

