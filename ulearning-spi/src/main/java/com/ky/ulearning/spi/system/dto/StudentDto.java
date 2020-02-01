package com.ky.ulearning.spi.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * {@link com.ky.ulearning.spi.system.entity.StudentEntity}
 * 学生dto
 *
 * @author luyuhao
 * @since 20/01/18 22:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentDto extends BaseDto {
    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String stuNumber;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String stuName;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String stuPassword;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String stuGender;

    /**
     * 系部
     */
    @ApiModelProperty("系部")
    private String stuDept;

    /**
     * 班级
     */
    @ApiModelProperty("班级")
    private String stuClass;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String stuPhone;

    /**
     * Email
     */
    @ApiModelProperty("Email")
    private String stuEmail;

    /**
     * 照片URL
     */
    @ApiModelProperty(hidden = true)
    private String stuPhoto;

    /**
     * 上次登录时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;

    /**
     * 密码更新时间
     */
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date pwdUpdateTime;
}
