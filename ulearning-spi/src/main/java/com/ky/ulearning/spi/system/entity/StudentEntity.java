package com.ky.ulearning.spi.system.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学生实体类
 *
 * @author luyuhao
 * @since 2020/01/18 22:41
 */
@Data
@ApiModel("学生实体类")
@EqualsAndHashCode(callSuper = true)
public class StudentEntity extends BaseEntity {

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
    @JsonIgnore
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
    @ApiModelProperty("照片URL")
    private String stuPhoto;

    /**
	* 上次登录时间
	*/
    @ApiModelProperty("上次登录时间")
    private Date lastLoginTime;
}