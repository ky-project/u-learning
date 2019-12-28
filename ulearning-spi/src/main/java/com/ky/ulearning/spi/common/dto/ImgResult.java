package com.ky.ulearning.spi.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 验证码对象
 *
 * @author luyuhao
 * @date 19/12/07 02:08
 */
@ApiModel("验证码")
@Data
@AllArgsConstructor
public class ImgResult {

    @ApiModelProperty("base64编码，直接放于src中")
    private String img;

    @ApiModelProperty("随机uuid，保证验证码唯一")
    private String uuid;
}
