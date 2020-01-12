package com.ky.ulearning.spi.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * {@link com.ky.ulearning.spi.system.entity.TeacherEntity}
 *
 * @author luyuhao
 * @date 19/12/12 00:15
 */
@Data
public class TeacherDto extends BaseDto {
    /**
     * 工号
     */
    @ApiModelProperty(value = "教师工号")
    private String teaNumber;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "教师姓名")
    private String teaName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "教师密码", example = "12356")
    private String teaPassword;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", example = "男/女")
    private String teaGender;

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门")
    private String teaDept;

    /**
     * 职称
     */
    @ApiModelProperty(value = "职称")
    private String teaTitle;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String teaPhone;

    /**
     * Email
     */
    @ApiModelProperty(value = "Email")
    private String teaEmail;

    /**
     * 照片URL
     */
    @ApiModelProperty(hidden = true)
    private String teaPhoto;

    /**
     * 上次登陆时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;

}
