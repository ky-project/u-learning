package com.ky.ulearning.spi.teacher.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ky.ulearning.spi.common.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * {@link com.ky.ulearning.spi.system.entity.TeachingTaskEntity}
 * 教学任务dto
 *
 * @author luyuhao
 * @since 20/01/30 23:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeachingTaskNoticeDto extends BaseDto {
    /**
     * 教学任务ID
     */
    @ApiModelProperty("教学任务ID")
    private Long teachingTaskId;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    private String noticeTitle;

    /**
     * 内容
     */
    @ApiModelProperty("内容")
    private String noticeContent;

    /**
     * 附件URL
     */
    @ApiModelProperty("附件URL")
    private String noticeAttachment;

    /**
     * 是否共享
     */
    @ApiModelProperty("是否共享")
    private Boolean noticeShared;

    /**
     * 提交时间
     */
    @ApiModelProperty("提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date noticePostTime;

    /**
     * 关键词
     */
    @ApiModelProperty("关键词")
    private String noticeKeywords;

    /**
     * 附件名
     */
    @ApiModelProperty("附件名")
    private String noticeAttachmentName;

    /**
     * 附件大小
     */
    @ApiModelProperty(value = "附件大小", hidden = true)
    private String noticeAttachmentSize;
}
