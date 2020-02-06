package com.ky.ulearning.spi.monitor.dto;

import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * {@link com.ky.ulearning.spi.monitor.entity.FileRecordEntity}
 * 文件记录dto
 *
 * @author luyuhao
 * @since 20/02/06 16:58
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("文件记录dto")
public class FileRecordDto extends BaseDto {
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
