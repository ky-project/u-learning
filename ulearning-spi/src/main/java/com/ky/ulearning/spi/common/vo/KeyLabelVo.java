package com.ky.ulearning.spi.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * key-label数组vo
 *
 * @author luyuhao
 * @since 20/02/19 20:22
 */
@Data
@ApiModel("key-label数组vo")
public class KeyLabelVo {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String key;

    /**
     * 数据名
     */
    @ApiModelProperty("数据名")
    private String label;

    /**
     * 额外条件
     */
    @ApiModelProperty("额外条件")
    private Object condition;
}
