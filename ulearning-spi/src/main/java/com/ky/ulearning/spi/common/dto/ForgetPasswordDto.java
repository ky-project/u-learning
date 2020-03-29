package com.ky.ulearning.spi.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author luyuhao
 * @since 20/02/07 21:57
 */
@Data
@ApiModel("忘记密码dto")
@Accessors(chain = true)
public class ForgetPasswordDto implements Serializable {

    @ApiModelProperty(value = "用户id", hidden = true)
    private Long id;

    @ApiModelProperty(value = "用户角色", hidden = true)
    private String sysRole;

    @ApiModelProperty(value = "用户工号/学号", hidden = true)
    private String username;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户输入的验证码")
    private String code;

    @ApiModelProperty(value = "发送邮件生成的uuid")
    private String uuid;

    @ApiModelProperty(value = "用户类型 1：教师 2：学生")
    private Integer userType;

    @Override
    public String toString() {
        return "ForgetPasswordDto{" +
                "id=" + id +
                ", sysRole='" + sysRole + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='******'" +
                ", code='" + code + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
