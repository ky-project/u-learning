package com.ky.ulearning.spi.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author luyuhao
 * @date 2019/12/7 10:44
 */
@Data
@ApiModel("登录实体类")
public class LoginUser {
    @ApiModelProperty(value = "账号:学号/工号", example = "admin", required = true)
    @NotBlank
    private String username;

    @ApiModelProperty(value = "密码", example = "123456", required = true)
    @NotBlank
    private String password;

    @ApiModelProperty(value = "用户输入的验证码", example = "x6hf", required = true)
    @NotBlank
    private String code;

    @ApiModelProperty(value = "验证码生成的uuid", example = "55ac0bcf1f1044c69cd155c6393010ee", required = true)
    @NotBlank
    private String uuid = "";

    @Override
    public String toString() {
        return "{username=" + username + ", password= ******}";
    }
}
