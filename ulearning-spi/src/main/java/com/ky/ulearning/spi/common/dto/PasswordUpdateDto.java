package com.ky.ulearning.spi.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码dto
 *
 * @author luyuhao
 * @since 20/02/01 17:43
 */
@Data
@ApiModel("修改密码dto")
public class PasswordUpdateDto implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private Long id;

    /**
     * 旧密码
     */
    @ApiModelProperty(value = "旧密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @ApiModelProperty(value = "新密码")
    private String newPassword;


    @Override
    public String toString() {
        return "PasswordUpdateDto{" +
                "id=" + id +
                ", oldPassword='******'" +
                ", newPassword='******'" +
                '}';
    }
}
