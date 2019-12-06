package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.EncryptUtil;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.system.auth.service.TeacherService;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教师控制器
 *
 * @author luyuhao
 * @date 19/12/05 01:57
 */
@Slf4j
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

//    @Log("教师添加")
//    @ApiOperation(value = "教师添加", notes = "密码默认123456")
//    @PermissionName(source = "teacher:save", name = "教师添加", group = "教师管理")
//    @PostMapping("/save")
//    public ResponseEntity save(@Validated @RequestBody SysTeacherCreateDTO teacher) {
//        //获取操作者的编号
//        String userNumber = SecurityUtils.getTeaNumber();
//        //设置操作者编号
//        teacher.setCreateBy(userNumber);
//        //设置更新者编号
//        teacher.setUpdateBy(userNumber);
//        //密码加密
//        teacher.setTeaPassword(EncryptUtils.encryptPassword("123456"));
//        //TODO 设置初始头像url
//        sysTeacherService.save(teacher);
//        return ResponseEntity.ok(JsonResultUtil.success("添加教师成功"));
//    }
//
//    @Log("教师删除")
//    @ApiOperation(value = "教师删除")
//    @PermissionName(source = "teacher:delete", name = "教师删除", group = "教师管理")
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity delete(@PathVariable(value = "id") Long id) {
//        sysTeacherService.delete(id);
//        return ResponseEntity.ok(JsonResultUtil.success("教师删除成功"));
//    }
//
//    @Log("教师更新")
//    @ApiOperation(value = "教师更新")
//    @PermissionName(source = "teacher:update", name = "教师更新", group = "教师管理")
//    @PutMapping("/update")
//    public ResponseEntity update(@Validated @RequestBody SysTeacherUpdateDTO teacher) {
//        //获取操作者的编号
//        String teaNumber = SecurityUtils.getTeaNumber();
//        Long teaId = SecurityUtils.getTeaId();
//        //如果更新的是自身，则更新时间不变，防止token失效被弹出系统
//        if (teacher.getTeaId().equals(teaId)) {
//            teacher.setUpdateTime(SecurityUtils.getUpdateTime());
//        }
//        //设置更新者编号
//        teacher.setUpdateBy(teaNumber);
//        //若更新密码，需给密码加密
//        if (!StringUtils.isEmpty(teacher.getTeaPassword())) {
//            teacher.setTeaPassword(EncryptUtils.encryptPassword(teacher.getTeaPassword()));
//        }
//        sysTeacherService.update(teacher);
//        return ResponseEntity.ok(JsonResultUtil.success("更新教师成功"));
//    }
//
//    @Log("教师查询")
//    @ApiOperation(value = "教师查询", notes = "分页查询，支持多条件筛选")
//    @PermissionName(source = "teacher:query", name = "教师查询", group = "教师管理")
//    @GetMapping("/list")
//    public ResponseEntity getUsers(@Validated SysTeacherQueryCriteriaDTO sysTeacherQueryCriteriaDTO) {
//        if (sysTeacherQueryCriteriaDTO.getCurrentPage() != null && sysTeacherQueryCriteriaDTO.getPageSize() != null) {
//            sysTeacherQueryCriteriaDTO.setStartIndex((sysTeacherQueryCriteriaDTO.getCurrentPage() - 1) * sysTeacherQueryCriteriaDTO.getPageSize());
//        }
//        PagingDTO<SysTeacherDTO> pagingDTO = sysTeacherService.findByQueryCriteria(sysTeacherQueryCriteriaDTO);
//        return ResponseEntity.ok(JsonResultUtil.success(pagingDTO, "查询成功"));
//    }

    @Log("登录后台管理系统")
    @PostMapping("/login")
    public ResponseEntity login(TeacherEntity teacherEntity) {
        if (teacherEntity == null
                || StringUtils.isEmpty(teacherEntity.getTeaNumber())
                || StringUtils.isEmpty(teacherEntity.getTeaPassword())) {
            return ResponseEntity.badRequest().body((new JsonResult(SystemErrorCodeEnum.PARAMETER_EMPTY)));
        }
        TeacherEntity exists = teacherService.findByTeaNumber(teacherEntity.getTeaNumber());
        if (exists == null) {
            return ResponseEntity.badRequest().body((new JsonResult(SystemErrorCodeEnum.TEACHER_NOT_EXISTS)));
        }
        if (!exists.getTeaPassword().equals(EncryptUtil.encryptPassword(teacherEntity.getTeaPassword()))) {
            return ResponseEntity.badRequest().body((new JsonResult(SystemErrorCodeEnum.PASSWORD_ERROR)));
        }
        return ResponseEntity.ok().body(new JsonResult<>(exists));
    }
}
