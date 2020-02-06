package com.ky.ulearning.spi.monitor.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件记录实体类
 *
 * @author luyuhao
 * @since 2020/02/06 16:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("文件记录实体类")
public class FileRecordEntity extends BaseEntity {

    /**
     * 文件记录url
     */
    @ApiModelProperty("文件记录url")
    private String recordUrl;

    /**
     * 文件记录名
     */
    @ApiModelProperty("文件记录名")
    private String recordName;

    /**
     * 文件记录名大小
     */
    @ApiModelProperty("文件记录名大小")
    private Long recordSize;

    /**
     * 文件记录类型
     */
    @ApiModelProperty("文件记录类型")
    private String recordType;

    /**
     * 文件记录所属表
     */
    @ApiModelProperty("文件记录所属表")
    private String recordTable;

    /**
     * 文件记录所属表对应id
     */
    @ApiModelProperty("文件记录所属表对应id")
    private Long recordTableId;
}