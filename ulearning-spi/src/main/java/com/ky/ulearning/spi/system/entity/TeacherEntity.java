package com.ky.ulearning.spi.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 教师表实体类
 *
 * @author luyuhao
 * @date 2019/12/05 01:42
 */
@ApiModel("教师实体类")
@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherEntity extends BaseEntity {

    /**
	* 工号
	*/
    @ApiModelProperty("教师工号")
    private String teaNumber;

    /**
	* 姓名
	*/
    @ApiModelProperty("姓名")
    private String teaName;

    /**
	* 密码
	*/
    @ApiModelProperty("密码")
    @JsonIgnore
    private String teaPassword;

    /**
	* 性别
	*/
    @ApiModelProperty("性别")
    private String teaGender;

    /**
	* 部门
	*/
    @ApiModelProperty("部门")
    private String teaDept;

    /**
	* 职称
	*/
    @ApiModelProperty("职称")
    private String teaTitle;

    /**
	* 联系电话
	*/
    @ApiModelProperty("联系电话")
    private String teaPhone;

    /**
	* Email
	*/
    @ApiModelProperty("Email")
    private String teaEmail;

    /**
	* 照片URL
	*/
    @ApiModelProperty("照片URL")
    private String teaPhoto;

    /**
     * 上次登陆时间
     */
    @ApiModelProperty("上次登陆时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;
}