package com.ky.ulearning.teacher.controller;

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
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.teacher.dto.TeachingTaskNoticeDto;
import com.ky.ulearning.spi.teacher.entity.TeachingTaskNoticeEntity;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.service.TeachingTaskNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * 教学任务通告controller
 *
 * @author luyuhao
 * @since 20/01/30 23:39
 */
@Slf4j
@RestController
@Api(tags = "通告管理", description = "通告管理接口")
@RequestMapping(value = "/teachingTaskNotice")
public class TeachingTaskNoticeServiceController extends BaseController {

    @Autowired
    private TeachingTaskNoticeService teachingTaskNoticeService;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Log("教师-分页查询通告")
    @ApiOperation(value = "分页查询通告", notes = "只能查看自己教学任务的通告")
    @ApiOperationSupport(ignoreParameters = {"id", "noticeAttachment"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<TeachingTaskNoticeEntity>>> pageList(PageParam pageParam,
                                                                                   TeachingTaskNoticeDto teachingTaskNoticeDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(teachingTaskNoticeDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL);
        //权限校验
        teachingTaskValidUtil.checkTeachingTask(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), teachingTaskNoticeDto.getTeachingTaskId());
        PageBean<TeachingTaskNoticeEntity> pageBean = teachingTaskNoticeService.pageList(setPageParam(pageParam), teachingTaskNoticeDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("教师-添加通告")
    @ApiOperation(value = "添加通告", notes = "只能操作自己的教学任务")
    @ApiOperationSupport(ignoreParameters = {"id", "noticeAttachment", "noticeAttachmentName", "noticePostTime"})
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(MultipartFile attachment,
                                           TeachingTaskNoticeDto teachingTaskNoticeDto) throws IOException {
        ValidatorBuilder.build()
                .on( StringUtil.isEmpty(teachingTaskNoticeDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(teachingTaskNoticeDto.getNoticeTitle()), TeacherErrorCodeEnum.NOTICE_TITLE_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //权限校验
        teachingTaskValidUtil.checkTeachingTask(username, teachingTaskNoticeDto.getTeachingTaskId());
        //判断文件是否为空
        if(attachment != null && ! attachment.isEmpty()){
            ValidatorBuilder.build()
                    //文件类型篡改校验
                    .on(! FileUtil.fileTypeCheck(attachment), CommonErrorCodeEnum.FILE_TYPE_TAMPER)
                    //文件类型校验
                    .on(! FileUtil.fileTypeRuleCheck(attachment, FileUtil.ATTACHMENT_YTPE), CommonErrorCodeEnum.FILE_TYPE_ERROR)
                    //文件大小校验
                    .on(attachment.getSize() > defaultConfigParameters.getAttachmentMaxSize(), CommonErrorCodeEnum.FILE_SIZE_ERROR)
                    .doValidate().checkResult();
            String url = fastDfsClientWrapper.uploadFile(attachment);
            //设置附件url和name
            teachingTaskNoticeDto.setNoticeAttachment(url);
            teachingTaskNoticeDto.setNoticeAttachmentName(attachment.getOriginalFilename());
        }
        //设置创建/更新者和提交时间
        teachingTaskNoticeDto.setCreateBy(username);
        teachingTaskNoticeDto.setUpdateBy(username);
        teachingTaskNoticeDto.setNoticePostTime(new Date());
        teachingTaskNoticeService.save(teachingTaskNoticeDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
    }
}
