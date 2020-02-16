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
import com.ky.ulearning.spi.teacher.dto.CourseDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDocumentationDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.remoting.MonitorManageRemoting;
import com.ky.ulearning.teacher.service.CourseDocumentationService;
import com.ky.ulearning.teacher.service.CourseFileService;
import com.ky.ulearning.teacher.service.TeachingTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
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
import java.util.List;

/**
 * @author luyuhao
 * @since 20/02/14 20:33
 */
@Slf4j
@RestController
@Api(tags = "文件资料管理", description = "文件资料管理接口")
@RequestMapping(value = "/courseDocumentation")
public class CourseDocumentationController extends BaseController {

    @Autowired
    private CourseDocumentationService courseDocumentationService;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Autowired
    private MonitorManageRemoting monitorManageRemoting;

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Autowired
    private CourseFileService courseFileService;

    @Log("添加文件")
    @ApiOperationSupport(ignoreParameters = "id")
    @ApiOperation(value = "添加文件", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/saveFile")
    public ResponseEntity<JsonResult> saveFile(MultipartFile file, CourseDocumentationDto courseDocumentationDto) throws IOException {
        ValidatorBuilder.build()
                .ofNull(file, TeacherErrorCodeEnum.DOCUMENTATION_FILE_CANNOT_BE_NULL)
                .ofNull(courseDocumentationDto.getTeachingTaskId(), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .ofNull(courseDocumentationDto.getDocumentationTitle(), TeacherErrorCodeEnum.DOCUMENTATION_TITLE_CANNOT_BE_NULL)
                .ofNull(courseDocumentationDto.getDocumentationCategory(), TeacherErrorCodeEnum.DOCUMENTATION_CATEGORY_CANNOT_BE_NULL)
                .ofNull(courseDocumentationDto.getDocumentationShared(), TeacherErrorCodeEnum.DOCUMENTATION_SHARED_CANNOT_BE_NULL)
                .ofNull(courseDocumentationDto.getFileParentId(), TeacherErrorCodeEnum.DOCUMENTATION_PATH_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, courseDocumentationDto.getTeachingTaskId());
        //校验所属文件夹id
        CourseFileEntity courseFileEntity = teachingTaskValidUtil.checkCourseFileId(courseDocumentationDto.getFileParentId(), username);
        //保存文件
        String fileUrl = fastDfsClientWrapper.uploadFile(file);
        //创建课程文件对象
        CourseFileDto courseFileDto = createCourseFileDto(courseFileEntity.getId(), fileUrl, file, courseDocumentationDto.getFileParentId(), username);
        courseDocumentationDto.setUpdateBy(username);
        courseDocumentationDto.setCreateBy(username);
        //保存文件信息
        courseDocumentationService.save(courseDocumentationDto, courseFileDto);
        //监控记录文件上传
        monitorManageRemoting.add(getFileRecordDto(fileUrl, file, MicroConstant.COURSE_FILE_TABLE_NAME, courseFileDto.getId(), username));
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
    }

    /**
     * 根据参数创建课程文件对象
     */
    private CourseFileDto createCourseFileDto(Long courseId, String fileUrl, MultipartFile file, Long fileParentId, String username) {
        CourseFileDto courseFileDto = new CourseFileDto();
        courseFileDto.setCourseId(courseId);
        courseFileDto.setFileUrl(fileUrl);
        courseFileDto.setFileName(file.getOriginalFilename());
        courseFileDto.setFileSize(file.getSize());
        courseFileDto.setFileExt(FilenameUtils.getExtension(file.getOriginalFilename()));
        courseFileDto.setFileType(1);
        courseFileDto.setFileParentId(fileParentId);
        courseFileDto.setUpdateBy(username);
        courseFileDto.setCreateBy(username);
        return courseFileDto;
    }

    @Log("添加文件夹")
    @ApiOperationSupport(ignoreParameters = {"id", "fileType"})
    @ApiOperation(value = "添加文件夹", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/saveFolder")
    public ResponseEntity<JsonResult> saveFolder(CourseFileDto courseFileDto) {
        ValidatorBuilder.build()
                .ofNull(courseFileDto.getTeachingTaskId(), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .ofNull(courseFileDto.getFileName(), TeacherErrorCodeEnum.COURSE_FILE_NAME_CANNOT_BE_NULL)
                .ofNull(courseFileDto.getFileParentId(), TeacherErrorCodeEnum.DOCUMENTATION_PATH_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, courseFileDto.getTeachingTaskId());
        //校验所属文件夹id
        CourseFileEntity courseFileEntity = teachingTaskValidUtil.checkCourseFileId(courseFileDto.getFileParentId(), username);
        courseFileDto.setCourseId(courseFileEntity.getCourseId());
        courseFileDto.setFileType(MicroConstant.FOLDER_TYPE);
        courseFileDto.setUpdateBy(username);
        courseFileDto.setCreateBy(username);
        //保存文件信息
        courseFileService.save(courseFileDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
    }

    @Log("查询文件资料根节点")
    @ApiOperation(value = "查询文件资料根节点", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/getRootFolder")
    public ResponseEntity<JsonResult<CourseFileDocumentationDto>> getRootFolder(Long teachingTaskId) {
        ValidatorBuilder.build()
                .ofNull(teachingTaskId, TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, teachingTaskId);
        //获取教学任务对应的课程id
        Long courseId = teachingTaskService.getCourseIdById(teachingTaskId);
        //根据courseId和username查询所属用户的根路径id
        Long fileId = courseFileService.getByCourseIdAndUsername(courseId, username);
        CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationService.getByFileId(fileId);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileDocumentationDto));
    }

    @Log("查询文件资料列表")
    @ApiOperationSupport(ignoreParameters = {"id", "fileId"})
    @ApiOperation(value = "查询文件资料列表", notes = "如果fileParentId为空，则默认查询教师根目录文件夹信息，只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/list")
    public ResponseEntity<JsonResult<List<CourseFileDocumentationDto>>> list(CourseFileDocumentationDto courseFileDocumentationDto) {
        ValidatorBuilder.build()
                .ofNull(courseFileDocumentationDto.getTeachingTaskId(), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .ofNull(courseFileDocumentationDto.getFileParentId(), TeacherErrorCodeEnum.DOCUMENTATION_PATH_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, courseFileDocumentationDto.getTeachingTaskId());
        //校验课程文件
        teachingTaskValidUtil.checkCourseFileId(courseFileDocumentationDto.getFileParentId(), username);
        //获取文件资料集合
        List<CourseFileDocumentationDto> courseFileDocumentationDtoList = courseDocumentationService.getList(courseFileDocumentationDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileDocumentationDtoList));
    }

    @Log("查询文件资料")
    @ApiOperation(value = "查询文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<CourseFileDocumentationDto>> getById(Long id) {
        ValidateHandler.checkNull(id, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //参数校验
        CourseFileDocumentationDto courseFileDocumentationDto = teachingTaskValidUtil.checkDocumentationId(id, username);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileDocumentationDto));
    }

    @Log("更新文件资料")
    @ApiOperationSupport(ignoreParameters = {"teachingTaskId", "fileSize", "fileExt", "fileType", "fileParentId"})
    @ApiOperation(value = "更新文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(CourseFileDocumentationDto courseFileDocumentationDto) {
        ValidatorBuilder.build()
                .ofNull(courseFileDocumentationDto.getFileId(), TeacherErrorCodeEnum.COURSE_FILE_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        teachingTaskValidUtil.checkCourseFileId(courseFileDocumentationDto.getFileId(), username);
        //校验文件资料id
        if (StringUtil.isNotEmpty(courseFileDocumentationDto.getId())) {
            teachingTaskValidUtil.checkDocumentationId(courseFileDocumentationDto.getId(), username);
            CourseFileDocumentationDto courseFileDocumentationDtoCheck = courseDocumentationService.getById(courseFileDocumentationDto.getId());
            //校验id与fileId是否对应
            ValidateHandler.checkParameter(!courseFileDocumentationDtoCheck.getFileId().equals(courseFileDocumentationDto.getFileId()), TeacherErrorCodeEnum.COURSE_FILE_ID_ILLEGAL);
        }
        courseFileDocumentationDto.setUpdateBy(username);
        courseDocumentationService.update(courseFileDocumentationDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("删除文件资料")
    @ApiOperation(value = "删除文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/deleteFile")
    public ResponseEntity<JsonResult> deleteFile(Long id) {
        ValidateHandler.checkNull(id, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //参数校验
        CourseFileDocumentationDto courseFileDocumentationDto = teachingTaskValidUtil.checkDocumentationId(id, username);
        //删除文件
        CourseFileEntity courseFileEntity = courseFileService.getById(courseFileDocumentationDto.getFileId());
        fastDfsClientWrapper.deleteFile(courseFileEntity.getFileUrl());
        //删除数据库表记录
        courseDocumentationService.delete(courseFileDocumentationDto.getId(), courseFileDocumentationDto.getFileId(), username);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("删除文件夹")
    @ApiOperation(value = "删除文件夹", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/deleteFolder")
    public ResponseEntity<JsonResult> deleteFolder(Long fileId) {
        ValidateHandler.checkNull(fileId, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //参数校验
        CourseFileEntity courseFileEntity = teachingTaskValidUtil.checkCourseFileId(fileId, username);
        ValidateHandler.checkParameter(MicroConstant.FOLDER_TYPE != courseFileEntity.getFileType(), TeacherErrorCodeEnum.COURSE_FILE_TYPE_ILLEGAL);
        //删除记录
        courseFileService.delete(fileId, username);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("下载文件资料")
    @ApiOperation(value = "下载文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/download")
    public ResponseEntity download(Long id) {
        ValidateHandler.checkNull(id, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //参数校验
        CourseFileDocumentationDto courseFileDocumentationDto = teachingTaskValidUtil.checkDocumentationId(id, username);
        //查询文件
        CourseFileEntity courseFileEntity = courseFileService.getById(courseFileDocumentationDto.getFileId());
        ValidateHandler.checkParameter(! fastDfsClientWrapper.hasFile(courseFileEntity.getFileUrl()), TeacherErrorCodeEnum.COURSE_FILE_ILLEGAL);
        byte[] courseFileBytes = fastDfsClientWrapper.download(courseFileEntity.getFileUrl());
        //设置head
        HttpHeaders headers = new HttpHeaders();
        //文件的属性名
        headers.setContentDispositionFormData("attachment", new String(courseFileEntity.getFileName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        //响应内容是字节流
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntityUtil.ok(headers, courseFileBytes);
    }
}
