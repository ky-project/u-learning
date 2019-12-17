package com.ky.ulearning.spi.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数传递工具
 *
 * @author luyuhao
 * @date 2019/12/17 18:41
 */
@Data
public class PageParam implements Serializable {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    /**
     * 页大小
     */
    @ApiModelProperty(value = "页大小")
    private Integer pageSize;

    /**
     * 查询的起始索引
     */
    @ApiModelProperty(hidden = true)
    private Integer startIndex;

}
