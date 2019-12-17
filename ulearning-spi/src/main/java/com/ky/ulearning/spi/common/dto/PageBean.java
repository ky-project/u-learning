package com.ky.ulearning.spi.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 分页组件
 *
 * @author luyuhao
 * @date 2019/12/17 18:49
 */
@ApiModel("分页组件")
@Data
@Accessors(chain = true)
public class PageBean<T> implements Serializable {

    /**
     * 记录list
     */
    @ApiModelProperty("记录list")
    private List<T> content;

    /**
     * 总页数
     */
    @ApiModelProperty("总页数")
    private Integer totalPage;

    /**
     * 总记录数
     */
    @ApiModelProperty("总记录数")
    private Integer total;

    /**
     * 页大小
     */
    @ApiModelProperty("页大小")
    private Integer pageSize;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private Integer currentPage;

    /**
     * 是否有前一页
     */
    @ApiModelProperty("是否有前一页")
    private Boolean hasPre;

    /**
     * 是否有下一页
     */
    @ApiModelProperty("是否有下一页")
    private Boolean hasNext;
}
