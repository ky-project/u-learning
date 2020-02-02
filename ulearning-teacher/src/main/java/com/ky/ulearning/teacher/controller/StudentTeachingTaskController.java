package com.ky.ulearning.teacher.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.spi.teacher.dto.StudentTeachingTaskDto;
import com.ky.ulearning.teacher.common.constants.TeacherErrorCodeEnum;
import com.ky.ulearning.teacher.common.utils.TeachingTaskValidUtil;
import com.ky.ulearning.teacher.service.StudentService;
import com.ky.ulearning.teacher.service.StudentTeachingTaskService;
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

/**
 * 学生选课controller
 *
 * @author luyuhao
 * @since 20/01/30 00:35
 */
@Slf4j
@RestController
@Api(tags = "学生选课管理", description = "学生选课管理接口")
@RequestMapping(value = "/studentTeachingTask")
public class StudentTeachingTaskController extends BaseController {

    @Autowired
    private StudentTeachingTaskService studentTeachingTaskService;

    @Autowired
    private TeachingTaskValidUtil teachingTaskValidUtil;

    @Autowired
    private StudentService studentService;

    @Log("分页查询选课学生信息")
    @ApiOperation(value = "分页查询选课学生信息", notes = "只能查看选了自己课的学生信息")
    @ApiOperationSupport(ignoreParameters = {"id", "stuPassword"})
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<StudentEntity>>> pageList(PageParam pageParam, StudentDto studentDto, Long teachingTaskId) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(teachingTaskId), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL);
        //权限校验
        teachingTaskValidUtil.checkTeachingTask(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), teachingTaskId);
        PageBean<StudentEntity> pageBean = studentTeachingTaskService.pageStudentList(studentDto, teachingTaskId, setPageParam(pageParam));
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("根据id查询选课学生信息")
    @ApiOperation(value = "根据id查询选课学生信息", notes = "只能查看选了自己课的学生信息")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<StudentEntity>> getById(Long stuId) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(stuId), TeacherErrorCodeEnum.STUDENT_ID_CANNOT_BE_NULL);
        //权限校验
        teachingTaskValidUtil.checkStuId(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class), stuId);
        StudentEntity studentEntity = studentService.getById(stuId);
        return ResponseEntityUtil.ok(JsonResult.buildData(studentEntity));
    }

    @Log("移除选课学生信息")
    @ApiOperation(value = "移除选课学生信息", notes = "只能移除选了自己课的学生")
    @ApiOperationSupport(ignoreParameters = {"id", "memo"})
    @PostMapping("/remove")
    public ResponseEntity<JsonResult> remove(StudentTeachingTaskDto studentTeachingTaskDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(studentTeachingTaskDto.getTeachingTaskId()), TeacherErrorCodeEnum.TEACHING_TASK_ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(studentTeachingTaskDto.getStuId()), TeacherErrorCodeEnum.STUDENT_ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        //权限校验
        String username = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        teachingTaskValidUtil.checkStuId(username, studentTeachingTaskDto.getStuId());
        teachingTaskValidUtil.checkTeachingTask(username, studentTeachingTaskDto.getTeachingTaskId());

        studentTeachingTaskService.remove(studentTeachingTaskDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("移除成功"));
    }
}
