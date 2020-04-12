package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.constant.CommonErrorCodeEnum;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.constant.TableFileEnum;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.FileUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.ExperimentDto;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskExperimentDto;
import com.ky.ulearning.spi.teacher.vo.ExperimentAttachmentVo;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.remoting.MonitorManageRemoting;
import com.ky.ulearning.teacher.service.ActivityService;
import com.ky.ulearning.teacher.service.TeachingTaskExperimentService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
public class TeachingTaskExperimentController extends BaseController {

    @Autowired
    private TeachingTaskExperimentService teachingTaskExperimentService;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Autowired
    private MonitorManageRemoting monitorManageRemoting;

    @Autowired
    private ActivityService activityService;

    @Log("添加附件")
    @ApiOperation(value = "添加附件")
    @PostMapping("/uploadAttachment")
    public ResponseEntity<JsonResult<ExperimentAttachmentVo>> uploadAttachment(MultipartFile attachment) throws IOException {
        ValidateHandler.checkParameter(attachment == null, TeacherErrorCodeEnum.EXPERIMENT_ATTACHMENT_CANNOT_BE_NULL);
        ValidatorBuilder.build()
                //文件类型校验
                .on(!FileUtil.fileTypeRuleCheck(attachment, FileUtil.ATTACHMENT_TYPE), CommonErrorCodeEnum.FILE_TYPE_ERROR)
                //文件类型篡改校验
                .on(!FileUtil.fileTypeCheck(attachment), CommonErrorCodeEnum.FILE_TYPE_TAMPER)
                //文件大小校验
                .on(attachment.getSize() > defaultConfigParameters.getExperimentAttachmentMaxSize(), CommonErrorCodeEnum.FILE_SIZE_ERROR)
                .doValidate().checkResult();
        String attachmentUrl = fastDfsClientWrapper.uploadFile(attachment);
        //记录文件
        monitorManageRemoting.addFileRecord(getFileRecordDto(attachmentUrl, attachment,
                TableFileEnum.TEACHING_TASK_EXPERIMENT_TABLE.getTableName(), null, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class)));
        return ResponseEntityUtil.ok(JsonResult.buildData(new ExperimentAttachmentVo(attachmentUrl, attachment.getOriginalFilename())));
    }

    @Log("添加实验")
    @ApiOperation("添加实验")
    @ApiOperationSupport(ignoreParameters = {"id", "experimentOrder"})
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(ExperimentDto experimentDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(experimentDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(experimentDto.getExperimentTitle()), TeacherErrorCodeEnum.EXPERIMENT_TITLE_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //教学任务id校验
        teachingTaskValidUtil.checkTeachingTask(username, experimentDto.getTeachingTaskId());
        teachingTaskValidUtil.checkOperate(null, experimentDto.getTeachingTaskId());
        //设置更新者信息
        experimentDto.setUpdateBy(username);
        experimentDto.setCreateBy(username);
        teachingTaskExperimentService.save(experimentDto);

        activityService.experimentActivity(experimentDto.getId(), username);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(experimentDto.getId(), "添加成功"));
    }

    @Log(value = "根据id查询实验", devModel = true)
    @ApiOperation(value = "根据id查询实验", notes = "只能查看自己教学任务的实验")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<TeachingTaskExperimentDto>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), TeacherErrorCodeEnum.EXPERIMENT_ID_CANNOT_BE_NULL);
        TeachingTaskExperimentDto teachingTaskExperimentDto = teachingTaskValidUtil.checkExperimentId(id, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildData(teachingTaskExperimentDto));
    }

    @Log(value = "分页查询实验信息", devModel = true)
    @ApiOperation(value = "分页查询实验信息", notes = "只能查看自己教学任务的实验")
    @ApiOperationSupport(ignoreParameters = {"id", "experimentAttachment"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<TeachingTaskExperimentDto>>> pageList(PageParam pageParam,
                                                                                    ExperimentDto experimentDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(experimentDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL);
        teachingTaskValidUtil.checkTeachingTask(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), experimentDto.getTeachingTaskId());
        PageBean<TeachingTaskExperimentDto> pageBean = teachingTaskExperimentService.pageList(setPageParam(pageParam), experimentDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("更新实验")
    @ApiOperation("更新实验")
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(ExperimentDto experimentDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(experimentDto.getId()), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //获取实验信息
        TeachingTaskExperimentDto teachingTaskExperimentDto = teachingTaskValidUtil.checkExperimentId(experimentDto.getId(), username);
        teachingTaskValidUtil.checkOperate(null, teachingTaskExperimentDto.getTeachingTaskId());
        //教学任务校验
        if (StringUtil.isNotEmpty(experimentDto.getTeachingTaskId())) {
            teachingTaskValidUtil.checkTeachingTask(username, experimentDto.getTeachingTaskId());
        }
        //设置更新者信息
        experimentDto.setUpdateBy(username);
        teachingTaskExperimentService.update(experimentDto);

        //判断是否需要删除原图片
        if (StringUtil.isNotEmpty(teachingTaskExperimentDto.getExperimentAttachment())) {
            if (StringUtil.isEmpty(experimentDto.getExperimentAttachment())
                    || !teachingTaskExperimentDto.getExperimentAttachment().equals(experimentDto.getExperimentAttachment())) {
                fastDfsClientWrapper.deleteFile(teachingTaskExperimentDto.getExperimentAttachment());
            }
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("下载附件")
    @ApiOperation(value = "下载附件", notes = "只能操作自己教学任务的实验")
    @GetMapping("/downloadAttachment")
    public ResponseEntity downloadAttachment(Long id) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //获取实验信息
        TeachingTaskExperimentDto teachingTaskExperimentDto = teachingTaskValidUtil.checkExperimentId(id, username);
        //判断文件是否为空
        String attachmentUrl = teachingTaskExperimentDto.getExperimentAttachment();
        ValidateHandler.checkParameter(StringUtil.isEmpty(attachmentUrl), TeacherErrorCodeEnum.EXPERIMENT_ATTACHMENT_NOT_EXISTS);
        String attachmentName = StringUtil.isEmpty(teachingTaskExperimentDto.getExperimentAttachmentName()) ? "未命名" : teachingTaskExperimentDto.getExperimentAttachmentName();

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

    @Log("删除实验")
    @ApiOperation("删除实验")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //获取实验信息
        TeachingTaskExperimentDto teachingTaskExperimentDto = teachingTaskValidUtil.checkExperimentId(id, username);
        teachingTaskValidUtil.checkOperate(null, teachingTaskExperimentDto.getTeachingTaskId());
        //删除实验
        teachingTaskExperimentService.delete(id, username);
        //判断是否需要删除原图片
        if (StringUtil.isNotEmpty(teachingTaskExperimentDto.getExperimentAttachment())) {
            fastDfsClientWrapper.deleteFile(teachingTaskExperimentDto.getExperimentAttachment());
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("分享实验")
    @ApiOperation("分享实验")
    @PostMapping("/updateShared")
    public ResponseEntity<JsonResult> updateShared(Long id) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(id), TeacherErrorCodeEnum.ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //获取实验信息
        teachingTaskValidUtil.checkExperimentId(id, username);
        //更新是否更新字段
        teachingTaskExperimentService.updateShared(id, true, username);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }
}
