package com.ky.ulearning.spi.system.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
	* 是否是管理员
	*/
    private Short manage;

    /**
     * 上次登陆时间
     */
    private Date lastLoginTime;

    /**
	* 是否有效
	*/
    private Boolean valid;

    /**
	* 备注
	*/
    private String memo;

    /**
	* 创建时间
	*/
    private Date createTime;

    /**
	* 创建者
	*/
    private String createBy;

    /**
	* 更新时间
	*/
    private Date updateTime;

    /**
	* 更新者
	*/
    private String updateBy;
}