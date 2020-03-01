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
import com.ky.ulearning.spi.teacher.dto.CourseFileDto;
import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import com.ky.ulearning.spi.teacher.dto.CourseResourceDto;
import com.ky.ulearning.spi.teacher.entity.CourseFileEntity;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.CourseFileUtil;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.remoting.MonitorManageRemoting;
import com.ky.ulearning.teacher.service.CourseFileService;
import com.ky.ulearning.teacher.service.CourseResourceService;
import com.ky.ulearning.teacher.service.TeachingTaskService;
import io.swagger.annotations.Api;
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
 * 教学资源Controller
 *
 * @author luyuhao
 * @since 20/02/17 19:26
 */
@Slf4j
@RestController
@Api(tags = "教学资源管理", description = "教学资源管理接口")
@RequestMapping(value = "/courseResource")
public class CourseResourceController extends BaseController {

    @Autowired
    private CourseResourceService courseResourceService;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Autowired
    private MonitorManageRemoting monitorManageRemoting;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @Autowired
    private CourseFileService courseFileService;

    @Autowired
    private TeachingTaskService teachingTaskService;

    @Log("添加文件")
    @ApiOperationSupport(ignoreParameters = "id")
    @ApiOperation(value = "添加文件", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/saveFile")
    public ResponseEntity<JsonResult> saveFile(MultipartFile file, CourseResourceDto courseResourceDto) throws IOException {
        ValidatorBuilder.build()
                .ofNull(file, TeacherErrorCodeEnum.RESOURCE_FILE_CANNOT_BE_NULL)
                //文件类型篡改校验
                .on(!FileUtil.fileTypeCheck(file), CommonErrorCodeEnum.FILE_TYPE_TAMPER)
                .ofNull(courseResourceDto.getTeachingTaskId(), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .ofNull(courseResourceDto.getResourceType(), TeacherErrorCodeEnum.RESOURCE_TYPE_CANNOT_BE_NULL)
                .ofNull(courseResourceDto.getResourceShared(), TeacherErrorCodeEnum.RESOURCE_SHARED_CANNOT_BE_NULL)
                .ofNull(courseResourceDto.getFileParentId(), TeacherErrorCodeEnum.RESOURCE_PATH_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, courseResourceDto.getTeachingTaskId());
        //校验所属文件夹id
        CourseFileEntity courseFileEntity = teachingTaskValidUtil.checkCourseFileId(courseResourceDto.getFileParentId(), username);
        //校验teachingTaskId对应的courseId是否与courseFile对应的courseId一致
        teachingTaskValidUtil.checkTeachingTaskIdAndCourseId(courseResourceDto.getTeachingTaskId(), courseFileEntity.getCourseId());
        //同名文件校验 1. 查询文件夹下的所有文件资料;2. 判断是否有同名文件
        Set<String> fileNameSet = Optional.ofNullable(courseResourceService.getListByFileParentIdAndFileType(courseResourceDto.getFileParentId(), MicroConstant.FILE_TYPE))
                .map(courseFileResourceDtoList -> courseFileResourceDtoList.stream()
                        .map(CourseFileResourceDto::getFileName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        ValidateHandler.checkParameter(fileNameSet.contains(FileUtil.getFileNameNoEx(file.getOriginalFilename())), TeacherErrorCodeEnum.COURSE_FILE_NAME_ILLEGAL);
        //保存文件
        String fileUrl = fastDfsClientWrapper.uploadFile(file);
        //创建课程文件对象
        CourseFileDto courseFileDto = CourseFileUtil.createCourseFileDto(courseFileEntity.getCourseId(), fileUrl, file, courseResourceDto.getFileParentId(), username);
        courseResourceDto.setUpdateBy(username);
        courseResourceDto.setCreateBy(username);
        //保存文件信息
        courseResourceService.save(courseResourceDto, courseFileDto);
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
                .ofNull(courseFileDto.getFileParentId(), TeacherErrorCodeEnum.RESOURCE_PATH_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, courseFileDto.getTeachingTaskId());
        //校验所属文件夹id
        CourseFileEntity courseFileEntity = teachingTaskValidUtil.checkCourseFileId(courseFileDto.getFileParentId(), username);
        ValidateHandler.checkParameter(courseFileEntity.getFileType().equals(MicroConstant.FILE_TYPE), TeacherErrorCodeEnum.RESOURCE_CANNOT_BE_FOLDER);
        //校验teachingTaskId对应的courseId是否与courseFile对应的courseId一致
        teachingTaskValidUtil.checkTeachingTaskIdAndCourseId(courseFileDto.getTeachingTaskId(), courseFileEntity.getCourseId());
        //同名文件校验 1. 查询文件夹下的所有文件资料;2. 判断是否有同名文件
        Set<String> fileNameSet = Optional.ofNullable(courseResourceService.getListByFileParentIdAndFileType(courseFileDto.getFileParentId(), MicroConstant.FOLDER_TYPE))
                .map(courseFileResourceDtoList -> courseFileResourceDtoList.stream()
                        .map(CourseFileResourceDto::getFileName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        ValidateHandler.checkParameter(fileNameSet.contains(FileUtil.getFileNameNoEx(courseFileDto.getFileName())), TeacherErrorCodeEnum.COURSE_FILE_NAME_ILLEGAL);
        //设置基本属性
        courseFileDto.setCourseId(courseFileEntity.getCourseId());
        courseFileDto.setFileType(MicroConstant.FOLDER_TYPE);
        courseFileDto.setUpdateBy(username);
        courseFileDto.setCreateBy(username);
        //保存文件信息
        courseResourceService.save(CourseFileUtil.createCourseResourceDtoFolder(username), courseFileDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加成功"));
    }

    @Log("查询教学资源根节点")
    @ApiOperation(value = "查询教学资源根节点", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/getRootFolder")
    public ResponseEntity<JsonResult<CourseFileResourceDto>> getRootFolder(Long teachingTaskId) {
        ValidatorBuilder.build()
                .ofNull(teachingTaskId, TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, teachingTaskId);
        //获取教学任务对应的课程id
        Long courseId = teachingTaskService.getCourseIdById(teachingTaskId);
        //根据courseId和username查询所属用户的根路径id
        CourseFileResourceDto courseFileResourceDto = courseResourceService.getByCourseIdAndUsername(courseId, username, teachingTaskId);
        courseFileResourceDto.setFileName(courseFileResourceDto.getFileName().split("#")[0]);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileResourceDto));
    }

    @Log("查询教学资源列表")
    @ApiOperationSupport(ignoreParameters = {"id", "fileId"})
    @ApiOperation(value = "查询教学资源列表", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/list")
    public ResponseEntity<JsonResult<List<CourseFileResourceDto>>> list(CourseFileResourceDto courseFileResourceDto) {
        ValidatorBuilder.build()
                .ofNull(courseFileResourceDto.getTeachingTaskId(), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .ofNull(courseFileResourceDto.getFileParentId(), TeacherErrorCodeEnum.RESOURCE_PATH_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, courseFileResourceDto.getTeachingTaskId());
        //校验课程文件
        teachingTaskValidUtil.checkCourseFileId(courseFileResourceDto.getFileParentId(), username);
        //获取文件资料集合
        List<CourseFileResourceDto> courseFileResourceDtoList = courseResourceService.getList(courseFileResourceDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileResourceDtoList));
    }

    @Log("查询教学资源")
    @ApiOperation(value = "查询教学资源", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<CourseFileResourceDto>> getById(Long id) {
        ValidateHandler.checkNull(id, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //参数校验
        CourseFileResourceDto courseFileResourceDto = teachingTaskValidUtil.checkResourceId(id, username);
        //无法查询文件夹
        ValidateHandler.checkParameter((new Integer(MicroConstant.FOLDER_TYPE)).equals(courseFileResourceDto.getFileType()), TeacherErrorCodeEnum.RESOURCE_GET_BY_ID_ERROR);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileResourceDto));
    }

    @Log("更新教学资源")
    @ApiOperationSupport(ignoreParameters = {"teachingTaskId", "fileSize", "fileExt", "fileType", "fileParentId", "fileId", "resourceShared"})
    @ApiOperation(value = "更新教学资源", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(CourseFileResourceDto courseFileResourceDto) {
        ValidatorBuilder.build()
                .ofNull(courseFileResourceDto.getId(), TeacherErrorCodeEnum.RESOURCE_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        CourseFileResourceDto courseFileResourceDtoValid = teachingTaskValidUtil.checkResourceId(courseFileResourceDto.getId(), username);

        //同名文件校验 1. 查询文件夹下的所有文件资料;2. 判断是否有同名文件
        if (StringUtil.isNotEmpty(courseFileResourceDto.getFileName())) {
            List<CourseFileResourceDto> courseFileResourceDtoList = courseResourceService.getListByFileParentIdAndFileType(courseFileResourceDtoValid.getFileParentId(), courseFileResourceDtoValid.getFileType());
            for (CourseFileResourceDto fileResourceDto : courseFileResourceDtoList) {
                if (!fileResourceDto.getId().equals(courseFileResourceDto.getId())
                        && fileResourceDto.getFileName().equals(courseFileResourceDto.getFileName())) {
                    throw new BadRequestException(TeacherErrorCodeEnum.COURSE_FILE_NAME_ILLEGAL);
                }
            }
        }

        //设置基本属性
        courseFileResourceDto.setFileId(courseFileResourceDtoValid.getFileId());
        courseFileResourceDto.setUpdateBy(username);
        courseResourceService.update(courseFileResourceDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("删除教学资源")
    @ApiOperation(value = "删除教学资源", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> deleteFile(Long id) {
        ValidateHandler.checkNull(id, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //参数校验
        CourseFileResourceDto courseFileResourceDto = teachingTaskValidUtil.checkResourceId(id, username);
        //删除文件/文件夹
        deleteCourseFile(courseFileResourceDto, username);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("批量删除教学资源")
    @ApiOperation(value = "批量删除教学资源", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/batchDelete")
    public ResponseEntity<JsonResult> batchDelete(String ids) {
        ValidateHandler.checkNull(ids, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        String[] idArr = ids.split(",");
        for (String idStr : idArr) {
            Long id = Long.parseLong(idStr);
            //参数校验
            CourseFileResourceDto courseFileResourceDto = teachingTaskValidUtil.checkResourceId(id, username);
            //删除文件/文件夹
            deleteCourseFile(courseFileResourceDto, username);
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    /**
     * 删除文件/文件夹
     */
    private void deleteCourseFile(CourseFileResourceDto courseFileResourceDto, String username) {
        if ((new Integer(MicroConstant.FILE_TYPE)).equals(courseFileResourceDto.getFileType())) {
            CourseFileEntity courseFileEntity = courseFileService.getById(courseFileResourceDto.getFileId());
            //删除文件
            fastDfsClientWrapper.deleteFile(courseFileEntity.getFileUrl());
            //删除数据库表记录
            courseResourceService.delete(courseFileResourceDto.getId(), courseFileResourceDto.getFileId(), username);
        } else if ((new Integer(MicroConstant.FOLDER_TYPE)).equals(courseFileResourceDto.getFileType())) {
            //课程根目录和教师根目录无法删除
            ValidateHandler.checkParameter(courseFileResourceDto.getFileParentId().equals(MicroConstant.ROOT_FOLDER_PARENT_ID), TeacherErrorCodeEnum.COURSE_FILE_ROOT_ERROR);
            ValidateHandler.checkParameter(courseFileService.getById(courseFileResourceDto.getFileParentId()).getFileParentId().equals(MicroConstant.ROOT_FOLDER_PARENT_ID), TeacherErrorCodeEnum.COURSE_FILE_ROOT_ERROR);
            //初始化文件总集合
            List<CourseFileResourceDto> courseFileResourceDtoList = new ArrayList<>();
            courseFileResourceDtoList.add(courseFileResourceDto);
            //删除文件夹
            List<Long> fileParentIdList = new LinkedList<>();
            fileParentIdList.add(courseFileResourceDto.getFileId());
            //层级遍历文件夹
            while (!CollectionUtils.isEmpty(fileParentIdList)) {
                //查询索引为0的fileParentId对应的所有课程文件资料
                List<CourseFileResourceDto> tempList = courseResourceService.getListByFileParentId(fileParentIdList.get(0));
                //移除已查询完成的索引
                fileParentIdList.remove(0);
                //若本级无文件，直接结束操作
                if (CollectionUtils.isEmpty(tempList)) {
                    continue;
                }
                //加入到总集合中
                courseFileResourceDtoList.addAll(tempList);
                //遍历改层所有文件
                for (CourseFileResourceDto fileResourceDto : tempList) {
                    if ((new Integer(MicroConstant.FOLDER_TYPE)).equals(fileResourceDto.getFileType())) {
                        fileParentIdList.add(fileResourceDto.getFileId());
                    }
                }
            }
            //遍历删除课程文件资料/文件夹
            for (CourseFileResourceDto fileResourceDto : courseFileResourceDtoList) {
                //如果是文件，先删除文件
                if ((new Integer(MicroConstant.FILE_TYPE)).equals(fileResourceDto.getFileType())) {
                    //删除文件
                    CourseFileEntity courseFileEntity = courseFileService.getById(fileResourceDto.getFileId());
                    fastDfsClientWrapper.deleteFile(courseFileEntity.getFileUrl());
                }
                //删除记录
                courseResourceService.delete(fileResourceDto.getId(), fileResourceDto.getFileId(), username);
            }
        }
    }

    @Log("下载教学资源")
    @ApiOperation(value = "下载文件资料", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/download")
    public ResponseEntity download(Long id) {
        ValidateHandler.checkNull(id, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL);
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //参数校验
        CourseFileResourceDto courseFileResourceDto = courseResourceService.getById(id);
        //控制检验
        ValidateHandler.checkNull(courseFileResourceDto, TeacherErrorCodeEnum.RESOURCE_NOT_EXISTS);
        if (!courseFileResourceDto.getResourceShared()) {
            teachingTaskValidUtil.checkCourseFileId(courseFileResourceDto.getFileId(), username);
        }
        //无法下载文件夹
        ValidateHandler.checkParameter((new Integer(MicroConstant.FOLDER_TYPE)).equals(courseFileResourceDto.getFileType()), TeacherErrorCodeEnum.COURSE_FOLDER_CANNOT_DOWNLOAD);
        //查询文件
        CourseFileEntity courseFileEntity = courseFileService.getById(courseFileResourceDto.getFileId());
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

    @Log("分享教学资源")
    @ApiOperation(value = "分享教学资源", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/updateShared")
    public ResponseEntity<JsonResult> updateShared(Long id, Boolean resourceShared) {
        ValidatorBuilder.build()
                .ofNull(id, TeacherErrorCodeEnum.ID_CANNOT_BE_NULL)
                .ofNull(resourceShared, TeacherErrorCodeEnum.RESOURCE_SHARED_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        CourseFileResourceDto courseFileResourceDto = teachingTaskValidUtil.checkResourceId(id, username);
        //若修改了分享字段，进行更新
        if (!resourceShared.equals(courseFileResourceDto.getResourceShared())) {
            courseResourceService.updateShared(id, resourceShared, username);
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("分享成功"));
    }

    @Log("批量分享教学资源")
    @ApiOperation(value = "批量分享教学资源", notes = "只能查询/操作属于自己的教学任务的数据")
    @PostMapping("/batchUpdateShared")
    public ResponseEntity<JsonResult> batchUpdateShared(String ids, Boolean resourceShared) {
        ValidatorBuilder.build()
                .ofNull(ids, TeacherErrorCodeEnum.DOCUMENTATION_ID_CANNOT_BE_NULL)
                .ofNull(resourceShared, TeacherErrorCodeEnum.RESOURCE_SHARED_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        String[] idArr = ids.split(",");
        for (String idStr : idArr) {
            Long id = Long.parseLong(idStr);
            CourseFileResourceDto courseFileResourceDto = teachingTaskValidUtil.checkResourceId(id, username);
            //若修改了分享字段，进行更新
            if (!resourceShared.equals(courseFileResourceDto.getResourceShared())) {
                courseResourceService.updateShared(id, resourceShared, username);
            }
        }
        return ResponseEntityUtil.ok(JsonResult.buildMsg("分享成功"));
    }

    @Log("查询教学资源分享区根节点id")
    @ApiOperation(value = "查询教学资源分享区根节点id", notes = "只能查询/操作属于自己的教学任务的数据")
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
        Long fileId = courseResourceService.getSharedByCourseId(courseId);
        return ResponseEntityUtil.ok(JsonResult.buildData(fileId));
    }

    @Log("查询教学资源分享区列表")
    @ApiOperationSupport(ignoreParameters = {"id", "fileId"})
    @ApiOperation(value = "查询教学资源分享区列表", notes = "只能查询/操作属于自己的教学任务的数据")
    @GetMapping("/sharedList")
    public ResponseEntity<JsonResult<List<CourseFileResourceDto>>> sharedList(CourseFileResourceDto courseFileResourceDto) {
        ValidatorBuilder.build()
                .ofNull(courseFileResourceDto.getTeachingTaskId(), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .ofNull(courseFileResourceDto.getFileParentId(), TeacherErrorCodeEnum.RESOURCE_PATH_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, courseFileResourceDto.getTeachingTaskId());
        //获取文件资料集合
        List<CourseFileResourceDto> courseFileResourceDtoList = courseResourceService.getSharedList(courseFileResourceDto);
        //将教学任务fileName去除#
        CourseFileResourceDto pre1 = courseResourceService.getByFileId(courseFileResourceDto.getFileParentId());
        if (StringUtil.isNotEmpty(pre1)) {
            CourseFileEntity pre2 = courseFileService.getById(pre1.getFileParentId());
            if (StringUtil.isNotEmpty(pre2) && (new Long(MicroConstant.ROOT_FOLDER_PARENT_ID).equals(pre2.getFileParentId()))) {
                for (CourseFileResourceDto fileResourceDto : courseFileResourceDtoList) {
                    fileResourceDto.setFileName(fileResourceDto.getFileName().split("#")[0]);
                }
            }
        }
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileResourceDtoList));
    }
}
