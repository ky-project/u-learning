package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.constant.CommonErrorCodeEnum;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.constant.TableFileEnum;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.FileUtil;
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
import com.ky.ulearning.teacher.common.utils.CourseFileUtil;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.remoting.MonitorManageRemoting;
import com.ky.ulearning.teacher.service.CourseDocumentationService;
import com.ky.ulearning.teacher.service.CourseFileService;
import com.ky.ulearning.teacher.service.TeachingTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

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
                //文件类型篡改校验
                .on(!FileUtil.fileTypeCheck(file), CommonErrorCodeEnum.FILE_TYPE_TAMPER)
                .ofNull(courseDocumentationDto.getTeachingTaskId(), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .ofNull(courseDocumentationDto.getDocumentationCategory(), TeacherErrorCodeEnum.DOCUMENTATION_CATEGORY_CANNOT_BE_NULL)
                .ofNull(courseDocumentationDto.getDocumentationShared(), TeacherErrorCodeEnum.DOCUMENTATION_SHARED_CANNOT_BE_NULL)
                .ofNull(courseDocumentationDto.getFileParentId(), TeacherErrorCodeEnum.DOCUMENTATION_PATH_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, courseDocumentationDto.getTeachingTaskId());
        //校验所属文件夹id
        CourseFileEntity courseFileEntity = teachingTaskValidUtil.checkCourseFileId(courseDocumentationDto.getFileParentId(), username);
        //校验teachingTaskId对应的courseId是否与courseFile对应的courseId一致
        teachingTaskValidUtil.checkTeachingTaskIdAndCourseId(courseDocumentationDto.getTeachingTaskId(), courseFileEntity.getCourseId());
        ValidateHandler.checkParameter(courseFileEntity.getFileParentId().equals(MicroConstant.ROOT_FOLDER_PARENT_ID), TeacherErrorCodeEnum.COURSE_FILE_ROOT_ERROR);
        //同名文件校验 1. 查询文件夹下的所有文件资料;2. 判断是否有同名文件
        Set<String> fileNameSet = Optional.ofNullable(courseDocumentationService.getListByFileParentIdAndFileType(courseDocumentationDto.getFileParentId(), MicroConstant.FILE_TYPE))
                .map(courseFileDocumentationDtoList -> courseFileDocumentationDtoList.stream()
                        .map(CourseFileDocumentationDto::getFileName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        ValidateHandler.checkParameter(fileNameSet.contains(FileUtil.getFileNameNoEx(file.getOriginalFilename())), TeacherErrorCodeEnum.COURSE_FILE_NAME_ILLEGAL);
        //保存文件
        String fileUrl = fastDfsClientWrapper.uploadFile(file);
        //创建课程文件对象
        CourseFileDto courseFileDto = CourseFileUtil.createCourseFileDto(courseFileEntity.getCourseId(), fileUrl, file, courseDocumentationDto.getFileParentId(), username);
        courseDocumentationDto.setUpdateBy(username);
        courseDocumentationDto.setCreateBy(username);
        //保存文件信息
        courseDocumentationService.save(courseDocumentationDto, courseFileDto);
        //监控记录文件上传
        monitorManageRemoting.add(getFileRecordDto(fileUrl, file, TableFileEnum.COURSE_FILE_TABLE.getTableName(), courseFileDto.getId(), username));
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
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
        ValidateHandler.checkParameter(courseFileEntity.getFileType().equals(MicroConstant.FILE_TYPE), TeacherErrorCodeEnum.DOCUMENTATION_CANNOT_BE_FOLDER);
        //校验teachingTaskId对应的courseId是否与courseFile对应的courseId一致
        teachingTaskValidUtil.checkTeachingTaskIdAndCourseId(courseFileDto.getTeachingTaskId(), courseFileEntity.getCourseId());
        //同名文件校验 1. 查询文件夹下的所有文件资料;2. 判断是否有同名文件
        Set<String> fileNameSet = Optional.ofNullable(courseDocumentationService.getListByFileParentIdAndFileType(courseFileDto.getFileParentId(), MicroConstant.FOLDER_TYPE))
                .map(courseFileDocumentationDtoList -> courseFileDocumentationDtoList.stream()
                        .map(CourseFileDocumentationDto::getFileName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        ValidateHandler.checkParameter(fileNameSet.contains(FileUtil.getFileNameNoEx(courseFileDto.getFileName())), TeacherErrorCodeEnum.COURSE_FILE_NAME_ILLEGAL);
        //设置基本信息
        courseFileDto.setCourseId(courseFileEntity.getCourseId());
        courseFileDto.setFileType(MicroConstant.FOLDER_TYPE);
        courseFileDto.setUpdateBy(username);
        courseFileDto.setCreateBy(username);
        //保存文件信息
        courseDocumentationService.save(CourseFileUtil.createCourseDocumentationDtoFolder(username), courseFileDto);
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
        CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationService.getByCourseIdAndUsername(courseId, username, teachingTaskId);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileDocumentationDto));
    }

    @Log("查询文件资料列表")
    @ApiOperationSupport(ignoreParameters = {"id", "fileId"})
    @ApiOperation(value = "查询文件资料列表", notes = "只能查询/操作属于自己的教学任务的数据")
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
        //无法查询文件夹
        ValidateHandler.checkParameter((new Integer(MicroConstant.FOLDER_TYPE)).equals(courseFileDocumentationDto.getFileType()), TeacherErrorCodeEnum.DOCUMENTATION_GET_BY_ID_ERROR);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileDocumentationDto));
    }

    @Log("更新文件资料")
    @ApiOperationSupport(ignoreParameters = {"teachingTaskId", "fileSize", "fileExt", "fileType", "fileParentId", "fileId", "documentationShared"})
    @ApiOperation(value = "更新文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(CourseFileDocumentationDto courseFileDocumentationDto) {
        ValidatorBuilder.build()
                .ofNull(courseFileDocumentationDto.getId(), TeacherErrorCodeEnum.DOCUMENTATION_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        CourseFileDocumentationDto courseFileDocumentationDtoValid = teachingTaskValidUtil.checkDocumentationId(courseFileDocumentationDto.getId(), username);

        //同名文件校验 1. 查询文件夹下的所有文件资料;2. 判断是否有同名文件
        if (StringUtil.isNotEmpty(courseFileDocumentationDto.getFileName())) {
            List<CourseFileDocumentationDto> courseFileDocumentationDtoList = courseDocumentationService.getListByFileParentIdAndFileType(courseFileDocumentationDtoValid.getFileParentId(), courseFileDocumentationDtoValid.getFileType());
            for (CourseFileDocumentationDto fileDocumentationDto : courseFileDocumentationDtoList) {
                if (!fileDocumentationDto.getId().equals(courseFileDocumentationDto.getId())
                        && fileDocumentationDto.getFileName().equals(courseFileDocumentationDto.getFileName())) {
                    throw new BadRequestException(TeacherErrorCodeEnum.COURSE_FILE_NAME_ILLEGAL);
                }
            }
        }

        //设置基本属性
        courseFileDocumentationDto.setFileId(courseFileDocumentationDtoValid.getFileId());
        courseFileDocumentationDto.setUpdateBy(username);
        courseDocumentationService.update(courseFileDocumentationDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("删除文件资料")
    @ApiOperation(value = "删除文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> deleteFile(Long id) {
        ValidateHandler.checkNull(id, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //参数校验
        CourseFileDocumentationDto courseFileDocumentationDto = teachingTaskValidUtil.checkDocumentationId(id, username);
        //删除文件/文件夹
        deleteCourseFile(courseFileDocumentationDto, username);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("批量删除文件资料")
    @ApiOperation(value = "批量删除文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/batchDelete")
    public ResponseEntity<JsonResult> batchDelete(String ids) {
        ValidateHandler.checkNull(ids, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        String[] idArr = ids.split(",");
        for (String idStr : idArr) {
            Long id = Long.parseLong(idStr);
            //参数校验
            CourseFileDocumentationDto courseFileDocumentationDto = teachingTaskValidUtil.checkDocumentationId(id, username);
            //删除文件/文件夹
            deleteCourseFile(courseFileDocumentationDto, username);
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    /**
     * 删除文件/文件夹
     */
    private void deleteCourseFile(CourseFileDocumentationDto courseFileDocumentationDto, String username) {
        if ((new Integer(MicroConstant.FILE_TYPE)).equals(courseFileDocumentationDto.getFileType())) {
            CourseFileEntity courseFileEntity = courseFileService.getById(courseFileDocumentationDto.getFileId());
            //删除文件
            fastDfsClientWrapper.deleteFile(courseFileEntity.getFileUrl());
            //删除数据库表记录
            courseDocumentationService.delete(courseFileDocumentationDto.getId(), courseFileDocumentationDto.getFileId(), username);
        } else if ((new Integer(MicroConstant.FOLDER_TYPE)).equals(courseFileDocumentationDto.getFileType())) {
            //课程根目录和教师根目录无法删除
            ValidateHandler.checkParameter(courseFileDocumentationDto.getFileParentId().equals(MicroConstant.ROOT_FOLDER_PARENT_ID), TeacherErrorCodeEnum.COURSE_FILE_ROOT_ERROR);
            ValidateHandler.checkParameter(courseFileService.getById(courseFileDocumentationDto.getFileParentId()).getFileParentId().equals(MicroConstant.ROOT_FOLDER_PARENT_ID), TeacherErrorCodeEnum.COURSE_FILE_ROOT_ERROR);
            //初始化文件总集合
            List<CourseFileDocumentationDto> courseFileDocumentationDtoList = new ArrayList<>();
            courseFileDocumentationDtoList.add(courseFileDocumentationDto);
            //删除文件夹
            List<Long> fileParentIdList = new LinkedList<>();
            fileParentIdList.add(courseFileDocumentationDto.getFileId());
            //层级遍历文件夹
            while (!CollectionUtils.isEmpty(fileParentIdList)) {
                //查询索引为0的fileParentId对应的所有课程文件资料
                List<CourseFileDocumentationDto> tempList = courseDocumentationService.getListByFileParentId(fileParentIdList.get(0));
                //移除已查询完成的索引
                fileParentIdList.remove(0);
                //若本级无文件，直接结束操作
                if (CollectionUtils.isEmpty(tempList)) {
                    continue;
                }
                //加入到总集合中
                courseFileDocumentationDtoList.addAll(tempList);
                //遍历改层所有文件
                for (CourseFileDocumentationDto fileDocumentationDto : tempList) {
                    if ((new Integer(MicroConstant.FOLDER_TYPE)).equals(fileDocumentationDto.getFileType())) {
                        fileParentIdList.add(fileDocumentationDto.getFileId());
                    }
                }
            }
            //遍历删除课程文件资料/文件夹
            for (CourseFileDocumentationDto fileDocumentationDto : courseFileDocumentationDtoList) {
                //如果是文件，先删除文件
                if ((new Integer(MicroConstant.FILE_TYPE)).equals(fileDocumentationDto.getFileType())) {
                    //删除文件
                    CourseFileEntity courseFileEntity = courseFileService.getById(fileDocumentationDto.getFileId());
                    fastDfsClientWrapper.deleteFile(courseFileEntity.getFileUrl());
                }
                //删除记录
                courseDocumentationService.delete(fileDocumentationDto.getId(), fileDocumentationDto.getFileId(), username);
            }
        }
    }

    @Log("下载文件资料")
    @ApiOperation(value = "下载文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/download")
    public ResponseEntity download(Long id) {
        ValidateHandler.checkNull(id, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //参数校验
        CourseFileDocumentationDto courseFileDocumentationDto = courseDocumentationService.getById(id);
        //空值校验
        ValidateHandler.checkNull(courseFileDocumentationDto, TeacherErrorCodeEnum.DOCUMENTATION_NOT_EXISTS);
        //若为非共享文件，进行可操作验证
        if(! courseFileDocumentationDto.getDocumentationShared()){
            //校验课程文件id
            teachingTaskValidUtil.checkCourseFileId(courseFileDocumentationDto.getFileId(), username);
        }
        //无法下载文件夹
        ValidateHandler.checkParameter((new Integer(MicroConstant.FOLDER_TYPE)).equals(courseFileDocumentationDto.getFileType()), TeacherErrorCodeEnum.COURSE_FOLDER_CANNOT_DOWNLOAD);
        //查询文件
        CourseFileEntity courseFileEntity = courseFileService.getById(courseFileDocumentationDto.getFileId());
        ValidateHandler.checkParameter(!fastDfsClientWrapper.hasFile(courseFileEntity.getFileUrl()), TeacherErrorCodeEnum.COURSE_FILE_ILLEGAL);
        byte[] courseFileBytes = fastDfsClientWrapper.download(courseFileEntity.getFileUrl());
        //设置head
        HttpHeaders headers = new HttpHeaders();
        //文件的属性名
        headers.setContentDispositionFormData("attachment", new String((courseFileEntity.getFileName() + "." + courseFileEntity.getFileExt()).getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        //响应内容是字节流
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntityUtil.ok(headers, courseFileBytes);
    }

    @Log("分享文件资料")
    @ApiOperation(value = "分享文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/updateShared")
    public ResponseEntity<JsonResult> updateShared(Long id, Boolean documentationShared) {
        ValidatorBuilder.build()
                .ofNull(id, TeacherErrorCodeEnum.DOCUMENTATION_ID_CANNOT_BE_NULL)
                .ofNull(documentationShared, TeacherErrorCodeEnum.DOCUMENTATION_SHARED_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        CourseFileDocumentationDto courseFileDocumentationDto = teachingTaskValidUtil.checkDocumentationId(id, username);
        //若修改了分享字段，进行更新
        if(! documentationShared.equals(courseFileDocumentationDto.getDocumentationShared())){
            courseDocumentationService.updateShared(id, documentationShared, username);
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("分享成功"));
    }

    @Log("批量分享文件资料")
    @ApiOperation(value = "批量分享文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/batchUpdateShared")
    public ResponseEntity<JsonResult> batchUpdateShared(String ids, Boolean documentationShared) {
        ValidatorBuilder.build()
                .ofNull(ids, TeacherErrorCodeEnum.DOCUMENTATION_ID_CANNOT_BE_NULL)
                .ofNull(documentationShared, TeacherErrorCodeEnum.DOCUMENTATION_SHARED_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        String[] idArr = ids.split(",");
        for (String idStr : idArr) {
            Long id = Long.parseLong(idStr);
            CourseFileDocumentationDto courseFileDocumentationDto = teachingTaskValidUtil.checkDocumentationId(id, username);
            //若修改了分享字段，进行更新
            if(! documentationShared.equals(courseFileDocumentationDto.getDocumentationShared())){
                courseDocumentationService.updateShared(id, documentationShared, username);
            }
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("分享成功"));
    }

    @Log("查询文件资料分享区根节点id")
    @ApiOperation(value = "查询文件资料分享区根节点id", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/getSharedRootFolder")
    public ResponseEntity<JsonResult<Long>> getSharedRootFolder(Long teachingTaskId) {
        ValidatorBuilder.build()
                .ofNull(teachingTaskId, TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, teachingTaskId);
        //获取教学任务对应的课程id
        Long courseId = teachingTaskService.getCourseIdById(teachingTaskId);
        //根据courseId和username查询所属用户的根路径id
        Long fileId = courseDocumentationService.getSharedByCourseId(courseId);
        return ResponseEntityUtil.ok(JsonResult.buildData(fileId));
    }

    @Log("查询文件资料分享区列表")
    @ApiOperationSupport(ignoreParameters = {"id", "fileId"})
    @ApiOperation(value = "查询文件资料分享区列表", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/sharedList")
    public ResponseEntity<JsonResult<List<CourseFileDocumentationDto>>> sharedList(CourseFileDocumentationDto courseFileDocumentationDto) {
        ValidatorBuilder.build()
                .ofNull(courseFileDocumentationDto.getTeachingTaskId(), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .ofNull(courseFileDocumentationDto.getFileParentId(), TeacherErrorCodeEnum.DOCUMENTATION_PATH_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, courseFileDocumentationDto.getTeachingTaskId());
        //获取文件资料集合
        List<CourseFileDocumentationDto> courseFileDocumentationDtoList = courseDocumentationService.getSharedList(courseFileDocumentationDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileDocumentationDtoList));
    }
}
