package com.ky.ulearning.spi.monitor.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author luyuhao
 * @since 20/02/12 02:28
 */
@Data
@ApiModel("访问量")
@AllArgsConstructor
@NoArgsConstructor
public class TrafficVo {

    /**
     * 日期
     */
    @ApiModelProperty("日期")
    private String date;

    /**
     * 访问量
     */
    @ApiModelProperty("访问量")
    private Long number;
}
