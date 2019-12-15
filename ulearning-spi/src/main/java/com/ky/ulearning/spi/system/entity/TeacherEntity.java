package com.ky.ulearning.spi.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ky.ulearning.spi.common.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 教师表实体类
 *
 * @author Darren
 * @date 2019/12/05 01:42
 */
@Data
public class TeacherEntity extends BaseEntity {

    /**
	* 工号
	*/
    private String teaNumber;

    /**
	* 姓名
	*/
    private String teaName;

    /**
	* 密码
	*/
    private String teaPassword;

    /**
	* 性别
	*/
    private String teaGender;

    /**
	* 部门
	*/
    private String teaDept;

    /**
	* 职称
	*/
    private String teaTitle;

    /**
	* 联系电话
	*/
    private String teaPhone;

    /**
	* Email
	*/
    private String teaEmail;

    /**
	* 照片URL
	*/
    private String teaPhoto;

    /**
     * 上次登陆时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;
}