package com.ky.ulearning.spi.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 学期vo
 *
 * @author luyuhao
 * @since 20/01/17 02:01
 */
@Data
@ApiModel("学期Vo对象")
@Accessors(chain = true)
public class TermVo implements Serializable {

    /**
     * 学期item
     */
    @ApiModelProperty("学期")
    String termItem;

}
