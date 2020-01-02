package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.EncryptUtil;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.dto.UserContext;
import com.ky.ulearning.spi.system.dto.TeacherDto;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.system.auth.service.RolePermissionService;
import com.ky.ulearning.system.auth.service.TeacherRoleService;
import com.ky.ulearning.system.auth.service.TeacherService;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 教师控制器
 *
 * @author luyuhao
 * @date 19/12/05 01:57
 */
@Slf4j
@RestController
@Api(tags = "教师管理", description = "教师管理接口")
@RequestMapping(value = "/teacher", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRoleService teacherRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;


    @Log("教师添加")
    @ApiOperationSupport(ignoreParameters = {"id", "teaPassword"})
    @ApiOperation(value = "教师添加", notes = "密码默认123456")
    @PermissionName(source = "teacher:save", name = "教师添加", group = "教师管理")
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(@Validated TeacherDto teacher) {
        if (StringUtil.isEmpty(teacher.getTeaName())) {
            return ResponseEntityUtil.badRequest((new JsonResult<>(SystemErrorCodeEnum.NAME_CANNOT_BE_NULL)));
        }
        if (StringUtil.isEmpty(teacher.getTeaNumber())) {
            return ResponseEntityUtil.badRequest((new JsonResult<>(SystemErrorCodeEnum.TEA_NUMBER_CANNOT_BE_NULL)));
        }
        if (StringUtil.isEmpty(teacher.getTeaEmail())) {
            return ResponseEntityUtil.badRequest((new JsonResult<>(SystemErrorCodeEnum.EMAIL_CANNOT_BE_NULL)));
        }
        //获取操作者的编号
        String userNumber = RequestHolderUtil.getHeaderByName(MicroConstant.USERNAME);
        //设置操作者编号
        teacher.setCreateBy(userNumber);
        //设置更新者编号
        teacher.setUpdateBy(userNumber);
        //密码加密
        teacher.setTeaPassword(EncryptUtil.encryptPassword("123456"));
        //TODO 设置初始头像url
        teacherService.save(teacher);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加教师成功"));
    }

    @Log("教师删除")
    @ApiOperation(value = "教师删除")
    @PermissionName(source = "teacher:delete", name = "教师删除", group = "教师管理")
    @DeleteMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        teacherService.delete(id);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("教师删除成功"));
    }

    @Log("教师查询")
    @ApiOperation(value = "教师查询", notes = "分页查询，支持多条件筛选")
    @PermissionName(source = "teacher:pageList", name = "教师查询", group = "教师管理")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<TeacherEntity>>> pageList(TeacherDto teacherDto,
                                                                        PageParam pageParam) {
        if (pageParam.getCurrentPage() != null && pageParam.getPageSize() != null) {
            pageParam.setStartIndex((pageParam.getCurrentPage() - 1) * pageParam.getPageSize());
        }
        PageBean<TeacherEntity> pageBean = teacherService.pageTeacherList(teacherDto, pageParam);
        return ResponseEntityUtil.ok(JsonResult.build(HttpStatus.OK.value(), "查询成功", pageBean));
    }

    @ApiOperation(value = "", hidden = true)
    @PostMapping("/login")
    public ResponseEntity<JsonResult<UserContext>> login(String teaNumber) {
        if (StringUtils.isEmpty(teaNumber)) {
            return ResponseEntityUtil.badRequest(new JsonResult<>(SystemErrorCodeEnum.PARAMETER_EMPTY));
        }
        //获取教师信息
        TeacherEntity teacher = teacherService.getByTeaNumber(teaNumber);
        if (teacher == null) {
            return ResponseEntityUtil.badRequest(new JsonResult<>(SystemErrorCodeEnum.TEACHER_NOT_EXISTS));
        }
        UserContext userContext = new UserContext()
                .setId(teacher.getId())
                .setSysRole(MicroConstant.SYS_ROLE_TEACHER)
                .setUsername(teacher.getTeaNumber())
                .setPassword(teacher.getTeaPassword())
                .setUpdateTime(teacher.getUpdateTime());

        //获取该教师的角色
        List<RoleEntity> roleList = teacherRoleService.getRoleByTeaId(teacher.getId());
        userContext.setRoles(roleList);
        //若有角色，则获取赋值
        if (!CollectionUtils.isEmpty(roleList)) {
            //抽取角色权限
            List<Long> roleIdList = roleList
                    .stream()
                    .map(RoleEntity::getId)
                    .collect(Collectors.toList());
            userContext.setPermissions(rolePermissionService.getPermissionListByRoleId(roleIdList));
        }
        return ResponseEntityUtil.ok(new JsonResult<>(userContext));
    }

    @Log("根据工号查询教师")
    @ApiOperation("根据工号查询教师")
    @PermissionName(source = "teacher:getByTeaNumber", name = "根据工号查询教师", group = "教师管理")
    @GetMapping("/getByTeaNumber")
    public ResponseEntity<JsonResult<TeacherEntity>> getByTeaNumber(String teaNumber) {
        if (StringUtils.isEmpty(teaNumber)) {
            return ResponseEntityUtil.badRequest((new JsonResult<>(SystemErrorCodeEnum.PARAMETER_EMPTY)));
        }
        TeacherEntity exists = teacherService.getByTeaNumber(teaNumber);
        return ResponseEntityUtil.ok((new JsonResult<>(exists)));
    }

    @Log("查询教师角色")
    @ApiOperation("查询教师角色")
    @PermissionName(source = "teacher:getAssignedRole", name = "查询教师角色", group = "教师管理")
    @GetMapping("/getAssignedRole")
    public ResponseEntity<JsonResult<List<RoleEntity>>> getAssignedRole(Long id) {
        if (id == null) {
            return ResponseEntityUtil.badRequest((new JsonResult<>(SystemErrorCodeEnum.PARAMETER_EMPTY)));
        }
        //将其转换为userContext，并获取角色list和权限list
        List<RoleEntity> rolePermissionDtoList = teacherRoleService.getRoleByTeaId(id);
        return ResponseEntityUtil.ok(new JsonResult<>(rolePermissionDtoList));
    }


    @Log("更新教师信息")
    @ApiOperation(value = "更新教师信息")
    @PermissionName(source = "teacher:update", name = "更新教师信息", group = "教师管理")
    @PutMapping("/update")
    public ResponseEntity<JsonResult<TeacherDto>> update(@Validated TeacherDto teacherDto) {
        if (teacherDto.getId() == null) {
            return ResponseEntityUtil.badRequest((new JsonResult<>(SystemErrorCodeEnum.ID_CANNOT_BE_NULL)));
        }
        if (!StringUtils.isEmpty(teacherDto.getTeaPassword())) {
            teacherDto.setTeaPassword(EncryptUtil.encryptPassword(teacherDto.getTeaPassword()));
        }
        teacherDto.setUpdateBy(RequestHolderUtil.getHeaderByName(MicroConstant.USERNAME));
        teacherService.update(teacherDto);
        return ResponseEntityUtil.ok(new JsonResult<>(teacherDto));
    }
}
