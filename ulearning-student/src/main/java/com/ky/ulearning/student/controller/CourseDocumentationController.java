package com.ky.ulearning.student.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import com.ky.ulearning.student.common.constants.StudentErrorCodeEnum;
import com.ky.ulearning.student.common.utils.StudentTeachingTaskUtil;
import com.ky.ulearning.student.service.CourseDocumentationService;
import com.ky.ulearning.student.service.TeachingTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//
//    @Log("查询教学资源根节点")
//    @ApiOperation(value = "查询教学资源根节点", notes = "只能查询/操作属于自己的教学任务的数据")
//    @GetMapping("/getRootFolder")
//    public ResponseEntity<JsonResult<CourseFileResourceDto>> getRootFolder(Long teachingTaskId) {
//        ValidatorBuilder.build()
//                .ofNull(teachingTaskId, StudentErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
//                .doValidate().checkResult();
//        Long stuId = RequestHolderUtil.getAttribute(MicroConstant.USER_ID, Long.class);
//        //校验教学任务id
//        studentTeachingTaskUtil.selectedTeachingTask(teachingTaskId, stuId);
//        //获取教学任务对应的课程id
//        Long courseId = teachingTaskService.getCourseIdById(teachingTaskId);
//        //根据courseId和username查询所属用户的根路径id
//        CourseFileResourceDto courseFileResourceDto = courseResourceService.getByCourseIdAndUsername(courseId, username, teachingTaskId);
//        return ResponseEntityUtil.ok(JsonResult.buildData(courseFileResourceDto));
//    }
}
