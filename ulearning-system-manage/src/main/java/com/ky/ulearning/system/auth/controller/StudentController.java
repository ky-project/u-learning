package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.EncryptUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.StudentDto;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.system.auth.service.StudentService;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luyuhao
 * @since 20/01/18 22:56
 */
@Slf4j
@RestController
@Api(tags = "学生管理", description = "学生管理接口")
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Log("学生添加")
    @ApiOperationSupport(ignoreParameters = {"id", "stuPassword"})
    @ApiOperation(value = "学生添加", notes = "密码默认123456")
    @PermissionName(source = "student:save", name = "学生添加", group = "学生管理")
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(StudentDto studentDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(studentDto.getStuNumber()), SystemErrorCodeEnum.STU_NUMBER_CONNOT_BE_NULL)
                .on(StringUtil.isEmpty(studentDto.getStuName()), SystemErrorCodeEnum.STU_NAME_CONNOT_BE_NULL)
                .doValidate().checkResult();
        //获取操作者的编号
        String userNumber = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //设置操作者编号
        studentDto.setCreateBy(userNumber);
        //设置更新者编号
        studentDto.setUpdateBy(userNumber);

        //密码加密
        studentDto.setStuPassword(EncryptUtil.encryptPassword("123456"));
        //TODO 设置初始头像url
        studentService.save(studentDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加学生成功"));
    }

    @Log("根据id查询学生信息")
    @ApiOperation(value = "根据id查询学生信息")
    @PermissionName(source = "student:getById", name = "根据id查询学生信息", group = "学生管理")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<StudentEntity>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);
        StudentEntity studentEntity = studentService.getById(id);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(studentEntity, "查询成功"));
    }

    @Log("分页查询学生信息")
    @ApiOperation(value = "分页查询学生信息")
    @ApiOperationSupport(ignoreParameters = {"id", "stuPassword"})
    @PermissionName(source = "student:pageList", name = "分页查询学生信息", group = "学生管理")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<StudentEntity>>> pageList(PageParam pageParam, StudentDto studentDto) {
        if (pageParam.getCurrentPage() != null && pageParam.getPageSize() != null) {
            pageParam.setStartIndex((pageParam.getCurrentPage() - 1) * pageParam.getPageSize());
        }
        PageBean<StudentEntity> pageBean = studentService.pageStudentList(studentDto, pageParam);
        return ResponseEntityUtil.ok(JsonResult.buildDataMsg(pageBean, "查询成功"));
    }

    @Log("删除学生")
    @ApiOperation(value = "删除学生")
    @PermissionName(source = "student:delete", name = "删除学生", group = "学生管理")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);
        studentService.delete(id, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除学生成功"));
    }

    @Log("学生更新")
    @ApiOperation(value = "学生更新")
    @PermissionName(source = "student:update", name = "学生更新", group = "学生管理")
    @PostMapping("/update")
    public ResponseEntity<JsonResult> update(StudentDto studentDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(studentDto.getId()), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);
        //获取操作者的编号
        String userNumber = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //设置更新者编号
        studentDto.setUpdateBy(userNumber);

        //密码加密
        studentDto.setStuPassword(EncryptUtil.encryptPassword("123456"));
        //TODO 设置初始头像url
        studentService.save(studentDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加学生成功"));
    }
}
