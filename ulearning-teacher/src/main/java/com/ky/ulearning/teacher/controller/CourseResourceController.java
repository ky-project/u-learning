package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
                .ofNull(courseResourceDto.getTeachingTaskId(), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .ofNull(courseResourceDto.getResourceTitle(), TeacherErrorCodeEnum.RESOURCE_TITLE_CANNOT_BE_NULL)
                .ofNull(courseResourceDto.getResourceType(), TeacherErrorCodeEnum.RESOURCE_TYPE_CANNOT_BE_NULL)
                .ofNull(courseResourceDto.getResourceShared(), TeacherErrorCodeEnum.RESOURCE_SHARED_CANNOT_BE_NULL)
                .ofNull(courseResourceDto.getFileParentId(), TeacherErrorCodeEnum.RESOURCE_PATH_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //校验教学任务id
        teachingTaskValidUtil.checkTeachingTask(username, courseResourceDto.getTeachingTaskId());
        //校验所属文件夹id
        CourseFileEntity courseFileEntity = teachingTaskValidUtil.checkCourseFileId(courseResourceDto.getFileParentId(), username);
        //保存文件
        String fileUrl = fastDfsClientWrapper.uploadFile(file);
        //创建课程文件对象

        CourseFileDto courseFileDto = CourseFileUtil.createCourseFileDto(courseFileEntity.getId(), fileUrl, file, courseResourceDto.getFileParentId(), username);
        courseResourceDto.setUpdateBy(username);
        courseResourceDto.setCreateBy(username);
        //保存文件信息
        courseResourceService.save(courseResourceDto, courseFileDto);
        //监控记录文件上传
        monitorManageRemoting.add(getFileRecordDto(fileUrl, file, MicroConstant.COURSE_FILE_TABLE_NAME, courseFileDto.getId(), username));
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
        courseFileDto.setCourseId(courseFileEntity.getCourseId());
        courseFileDto.setFileType(MicroConstant.FOLDER_TYPE);
        courseFileDto.setUpdateBy(username);
        courseFileDto.setCreateBy(username);
        //保存文件信息
        courseFileService.save(courseFileDto);
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
        Long fileId = courseFileService.getByCourseIdAndUsername(courseId, username);
        CourseFileResourceDto courseFileResourceDto = courseResourceService.getByFileId(fileId);
        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileResourceDto));
    }
}
