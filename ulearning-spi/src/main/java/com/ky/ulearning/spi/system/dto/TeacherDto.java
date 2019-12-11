package com.ky.ulearning.spi.system.dto;

import lombok.Data;

import java.util.Date;

/**
 * {@link com.ky.ulearning.spi.system.entity.TeacherEntity}
 *
 * @author luyuhao
 * @date 19/12/12 00:15
 */
@Data
public class TeacherDto {
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
    private Date lastLoginTime;

    /**
     * id
     */
    private Long id;
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
