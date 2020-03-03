package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.common.utils.StudentTeachingTaskUtil;
import com.ky.ulearning.student.service.CourseDocumentationService;
import com.ky.ulearning.student.service.TeachingTaskService;
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
import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/22 20:35
 */
@Slf4j
@RestController
@Api(tags = "文件资料管理", description = "文件资料管理接口")
@RequestMapping(value = "/courseDocumentation")
public class CourseDocumentationController extends BaseController {

    @Autowired
    private CourseDocumentationService courseDocumentationService;

    @Autowired
    private StudentTeachingTaskUtil studentTeachingTaskUtil;

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Log("查询教学资源根节点")
    @ApiOperation(value = "查询教学资源根节点", notes = "只能查看/操作已选教学任务的通告数据")
    @GetMapping("/getRootFolder")
    public ResponseEntity<JsonResult<CourseFileDocumentationDto>> getRootFolder(Long teachingTaskId) {
        ValidatorBuilder.build()
                .ofNull(teachingTaskId, StudentErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        //校验教学任务id
        studentTeachingTaskUtil.selectedTeachingTask(teachingTaskId, stuId);
        //根据courseId和username查询所属用户的根路径id
        CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationService.getByTeachingTaskId(teachingTaskId);
        courseFileDocumentationDto.setFileName(courseFileDocumentationDto.getFileName().split("#")[0]);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileDocumentationDto));
    }

    @Log("查询文件资料列表")
    @ApiOperationSupport(ignoreParameters = {"id", "fileId"})
    @ApiOperation(value = "查询文件资料列表", notes = "只能查看/操作已选教学任务的通告数据")
    @GetMapping("/list")
    public ResponseEntity<JsonResult<List<CourseFileDocumentationDto>>> list(CourseFileDocumentationDto courseFileDocumentationDto) {
        ValidatorBuilder.build()
                .ofNull(courseFileDocumentationDto.getTeachingTaskId(), StudentErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .ofNull(courseFileDocumentationDto.getFileParentId(), StudentErrorCodeEnum.DOCUMENTATION_PARENT_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        //校验教学任务id
        studentTeachingTaskUtil.selectedTeachingTask(courseFileDocumentationDto.getTeachingTaskId(), stuId);
        //校验课程文件
        studentTeachingTaskUtil.checkCourseFileId(courseFileDocumentationDto.getFileParentId(), courseFileDocumentationDto.getTeachingTaskId());
        //获取文件资料集合
        List<CourseFileDocumentationDto> courseFileDocumentationDtoList = courseDocumentationService.getList(courseFileDocumentationDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileDocumentationDtoList));
    }

    @Log("下载文件资料")
    @ApiOperation(value = "下载文件资料", notes = "只能查看/操作已选教学任务的通告数据")
    @GetMapping("/download")
    public ResponseEntity download(Long id) {
        ValidateHandler.checkNull(id, StudentErrorCodeEnum.ID_CANNOT_BE_NULL);
        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
        //参数校验
        CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationService.getById(id);
        //空值校验
        ValidateHandler.checkNull(courseFileDocumentationDto, StudentErrorCodeEnum.DOCUMENTATION_NOT_EXISTS);
        //无法下载文件夹
        ValidateHandler.checkParameter((new Integer(MicroConstant.FOLDER_TYPE)).equals(courseFileDocumentationDto.getFileType()), StudentErrorCodeEnum.COURSE_FOLDER_CANNOT_DOWNLOAD);
        //校验课程文件id
        CourseFileEntity courseFileEntity = studentTeachingTaskUtil.checkCourseFileIdByStuId(courseFileDocumentationDto.getFileId(), stuId);
        //查询文件
        ValidateHandler.checkParameter(!fastDfsClientWrapper.hasFile(courseFileEntity.getFileUrl()), StudentErrorCodeEnum.COURSE_FILE_TIME_OUT);
        byte[] courseFileBytes = fastDfsClientWrapper.download(courseFileEntity.getFileUrl());
        //设置head
        HttpHeaders headers = new HttpHeaders();
        //文件的属性名
        headers.setContentDispositionFormData("attachment", new String((courseFileEntity.getFileName() + "." + courseFileEntity.getFileExt()).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        //响应内容是字节流
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntityUtil.ok(headers, courseFileBytes);
    }

}
