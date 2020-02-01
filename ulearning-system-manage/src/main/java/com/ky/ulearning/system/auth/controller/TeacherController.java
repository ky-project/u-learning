package com.ky.ulearning.system.auth.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.constant.CommonErrorCodeEnum;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.*;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.common.dto.PasswordUpdateDto;
import com.ky.ulearning.spi.common.dto.UserContext;
import com.ky.ulearning.spi.system.dto.TeacherDto;
import com.ky.ulearning.spi.system.entity.RoleEntity;
import com.ky.ulearning.spi.system.entity.TeacherEntity;
import com.ky.ulearning.spi.system.vo.TeacherVo;
import com.ky.ulearning.system.auth.service.RolePermissionService;
import com.ky.ulearning.system.auth.service.TeacherRoleService;
import com.ky.ulearning.system.auth.service.TeacherService;
import com.ky.ulearning.system.common.constants.SystemErrorCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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
public class TeacherController extends BaseController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRoleService teacherRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;


    @Log("教师添加")
    @ApiOperationSupport(ignoreParameters = {"id", "teaPassword"})
    @ApiOperation(value = "教师添加", notes = "密码默认123456")
    @PermissionName(source = "teacher:save", name = "教师添加", group = "教师管理")
    @PostMapping("/save")
    public ResponseEntity<JsonResult> save(TeacherDto teacher) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(teacher.getTeaName()), SystemErrorCodeEnum.NAME_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(teacher.getTeaNumber()), SystemErrorCodeEnum.TEA_NUMBER_CANNOT_BE_NULL)
                .doValidate().checkResult();
        //获取操作者的编号
        String userNumber = RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class);
        //设置操作者编号
        teacher.setCreateBy(userNumber);
        //设置更新者编号
        teacher.setUpdateBy(userNumber);
        //密码加密
        teacher.setTeaPassword(EncryptUtil.encryptPassword("123456"));
        teacherService.save(teacher);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("添加教师成功"));
    }

    @Log("教师删除")
    @ApiOperation(value = "教师删除")
    @PermissionName(source = "teacher:delete", name = "教师删除", group = "教师管理")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);
        teacherService.delete(id, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildMsg("教师删除成功"));
    }

    @Log("教师查询")
    @ApiOperation(value = "教师查询", notes = "分页查询，支持多条件筛选")
    @PermissionName(source = "teacher:pageList", name = "教师查询", group = "教师管理")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<TeacherEntity>>> pageList(TeacherDto teacherDto,
                                                                        PageParam pageParam) {
        PageBean<TeacherEntity> pageBean = teacherService.pageTeacherList(teacherDto, setPageParam(pageParam));
        return ResponseEntityUtil.ok(JsonResult.build(HttpStatus.OK.value(), "查询成功", pageBean));
    }

    @ApiOperation(value = "", hidden = true)
    @PostMapping("/login")
    public ResponseEntity<JsonResult<UserContext>> login(String teaNumber) {
        ValidateHandler.checkParameter(StringUtils.isEmpty(teaNumber), SystemErrorCodeEnum.PARAMETER_EMPTY);
        //获取教师信息
        TeacherEntity teacher = teacherService.getByTeaNumber(teaNumber);
        ValidateHandler.checkParameter(teacher == null, SystemErrorCodeEnum.TEACHER_NOT_EXISTS);
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
        return ResponseEntityUtil.ok(JsonResult.buildData(userContext));
    }

    /**
     * 登录成功更新登录时间但不更新更新日期
     */
    @ApiOperation(value = "", hidden = true)
    @PostMapping("/loginUpdate")
    public ResponseEntity<JsonResult> updateLoginTime(TeacherDto teacherDto) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(teacherDto.getId()), SystemErrorCodeEnum.ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(teacherDto.getLastLoginTime()), SystemErrorCodeEnum.LAST_LOGIN_TIME_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(teacherDto.getUpdateTime()), SystemErrorCodeEnum.UPDATE_TIME_CANNOT_BE_NULL)
                .doValidate().checkResult();
        teacherService.updateLastLoginTime(teacherDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("根据工号查询教师")
    @ApiOperation("根据工号查询教师")
    @PermissionName(source = "teacher:getByTeaNumber", name = "根据工号查询教师", group = "教师管理")
    @GetMapping("/getByTeaNumber")
    public ResponseEntity<JsonResult<TeacherEntity>> getByTeaNumber(String teaNumber) {
        ValidateHandler.checkParameter(StringUtils.isEmpty(teaNumber), SystemErrorCodeEnum.PARAMETER_EMPTY);
        TeacherEntity exists = teacherService.getByTeaNumber(teaNumber);
        return ResponseEntityUtil.ok(JsonResult.buildData(exists));
    }

    @Log("查询教师角色")
    @ApiOperation("查询教师角色")
    @PermissionName(source = "teacher:getAssignedRole", name = "查询教师角色", group = "教师管理")
    @GetMapping("/getAssignedRole")
    public ResponseEntity<JsonResult<List<RoleEntity>>> getAssignedRole(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), SystemErrorCodeEnum.PARAMETER_EMPTY);
        List<RoleEntity> roleList = teacherRoleService.getRoleByTeaId(id);
        return ResponseEntityUtil.ok(JsonResult.buildData(roleList));
    }

    @Log("更新教师信息")
    @ApiOperation(value = "更新教师信息")
    @PermissionName(source = "teacher:update", name = "更新教师信息", group = "教师管理")
    @PostMapping("/update")
    public ResponseEntity<JsonResult<TeacherDto>> update(TeacherDto teacherDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(teacherDto.getId()), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);
        if (!StringUtil.isEmpty(teacherDto.getTeaPassword())) {
            teacherDto.setTeaPassword(EncryptUtil.encryptPassword(teacherDto.getTeaPassword()));
        }
        teacherDto.setUpdateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        teacherService.update(teacherDto);
        return ResponseEntityUtil.ok(JsonResult.buildData(teacherDto));
    }

    @Log("分配教师角色")
    @ApiOperation("分配教师角色")
    @ApiImplicitParam(name = "roleIds", value = "角色ids字符串，逗号分隔")
    @PermissionName(source = "teacher:saveAssignedRole", name = "分配教师角色", group = "教师管理")
    @PostMapping("/saveAssignedRole")
    public ResponseEntity<JsonResult> saveAssignedRole(Long teaId, String roleIds) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(teaId), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);

        teacherRoleService.saveAssignedRole(teaId, roleIds, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        return ResponseEntityUtil.ok(JsonResult.buildMsg("分配成功"));
    }

    @Log("根据id查询教师")
    @ApiOperation("根据id查询教师")
    @PermissionName(source = "teacher:getById", name = "根据id查询教师", group = "教师管理")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<TeacherEntity>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtils.isEmpty(id), SystemErrorCodeEnum.PARAMETER_EMPTY);
        TeacherEntity teacherEntity = teacherService.getById(id);
        return ResponseEntityUtil.ok(JsonResult.buildData(teacherEntity));
    }

    @Log("获取所有教师信息")
    @ApiOperation("获取所有教师信息")
    @PermissionName(source = "teacher:getAll", name = "获取所有教师信息", group = "教师管理")
    @GetMapping("/getAll")
    public ResponseEntity<JsonResult<List<TeacherVo>>> getAll() {
        List<TeacherVo> teacherVoList = teacherService.getAll();
        return ResponseEntityUtil.ok(JsonResult.buildData(teacherVoList));
    }

    @Log("上传头像")
    @ApiOperation("上传头像")
    @PermissionName(source = "teacher:uploadPhoto", name = "上传头像", group = "教师管理")
    @PostMapping("/uploadPhoto")
    public ResponseEntity<JsonResult> uploadPhoto(@RequestParam("photo") MultipartFile photo, @RequestParam("id")Long id) throws IOException, InterruptedException {
        ValidatorBuilder.build()
                //参数非空校验
                .on(StringUtil.isEmpty(id), SystemErrorCodeEnum.ID_CANNOT_BE_NULL)
                .on(photo == null || photo.isEmpty(), CommonErrorCodeEnum.FILE_CANNOT_BE_NULL)
                //文件类型篡改校验
                .on(! FileUtil.fileTypeCheck(photo), CommonErrorCodeEnum.FILE_TYPE_TAMPER)
                //文件类型校验
                .on(! FileUtil.fileTypeRuleCheck(photo, FileUtil.IMAGE_TYPE), CommonErrorCodeEnum.FILE_TYPE_ERROR)
                //文件大小校验
                .on(photo.getSize() > defaultConfigParameters.getPhotoMaxSize(), CommonErrorCodeEnum.FILE_SIZE_ERROR)
                .doValidate().checkResult();
        //获取用户信息
        TeacherEntity teacherEntity = teacherService.getById(id);
        //判断是否已有头像，有则先删除
        if(StringUtil.isNotEmpty(teacherEntity.getTeaPhoto())){
            fastDfsClientWrapper.deleteFile(teacherEntity.getTeaPhoto());
        }
        //保存文件
        String url = fastDfsClientWrapper.uploadFile(photo);
        //更新url
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(id);
        teacherDto.setTeaPhoto(url);
        teacherDto.setUpdateTime(teacherEntity.getUpdateTime());
        teacherDto.setUpdateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        teacherService.updateTeaPhoto(teacherDto);
        //返回信息
        return ResponseEntityUtil.ok(JsonResult.buildMsg("上传成功"));
    }

    @ApiOperation(value = "", hidden = true)
    @PostMapping("/updateUpdateTime")
    public ResponseEntity<JsonResult> updateUpdateTime(Long id, Date updateTime) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);
        ValidateHandler.checkParameter(StringUtil.isEmpty(updateTime), SystemErrorCodeEnum.UPDATE_TIME_CANNOT_BE_NULL);
        teacherService.updateUpdateTime(id, updateTime);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @ApiOperation(value = "", hidden = true)
    @PostMapping("/updateTeaPhoto")
    public ResponseEntity<JsonResult> updateTeaPhoto(TeacherDto teacherDto) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(teacherDto.getId()), SystemErrorCodeEnum.ID_CANNOT_BE_NULL);
        ValidateHandler.checkParameter(StringUtil.isEmpty(teacherDto.getTeaPhoto()), SystemErrorCodeEnum.TEA_PHOTO_CANNOT_BE_NULL);
        teacherService.updateTeaPhoto(teacherDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("更新成功"));
    }

    @Log("更新密码")
    @ApiOperation("更新密码")
    @PermissionName(source = "teacher:updatePassword", name = "更新密码", group = "教师管理")
    @PostMapping("/updatePassword")
    public ResponseEntity<JsonResult> updatePassword(PasswordUpdateDto passwordUpdateDto){
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(passwordUpdateDto.getId()), SystemErrorCodeEnum.ID_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(passwordUpdateDto.getOldPassword()), CommonErrorCodeEnum.OLD_PASSWORD_CANNOT_BE_NULL)
                .on(StringUtil.isEmpty(passwordUpdateDto.getNewPassword()), CommonErrorCodeEnum.NEW_PASSWORD_CANNOT_BE_NULL)
                .on(passwordUpdateDto.getNewPassword().equals(passwordUpdateDto.getOldPassword()), CommonErrorCodeEnum.PASSWORD_SAME)
                .doValidate().checkResult();
        //获取教师信息并校验
        TeacherEntity teacherEntity = teacherService.getById(passwordUpdateDto.getId());
        ValidateHandler.checkParameter(teacherEntity == null, SystemErrorCodeEnum.TEACHER_NOT_EXISTS);

        String oldPassword = EncryptUtil.encryptPassword(passwordUpdateDto.getOldPassword());
        String newPassword = EncryptUtil.encryptPassword(passwordUpdateDto.getNewPassword());
        //旧密码错误
        ValidateHandler.checkParameter(!oldPassword.equals(teacherEntity.getTeaPassword()), CommonErrorCodeEnum.OLD_PASSWORD_ERROR);
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(passwordUpdateDto.getId());
        teacherDto.setUpdateBy(RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        teacherDto.setTeaPassword(newPassword);
        teacherService.update(teacherDto);
        return ResponseEntityUtil.ok(JsonResult.buildMsg("修改成功"));
    }
}
