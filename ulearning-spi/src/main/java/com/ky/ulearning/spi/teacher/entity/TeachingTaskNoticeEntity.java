package com.ky.ulearning.spi.teacher.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 教学任务通告实体类
 *
 * @author luyuhao
 * @since 2020/01/30 23:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TeachingTaskNoticeEntity extends BaseEntity {

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
    private Date noticePostTime;

    /**
     * 关键词
     */
    @ApiModelProperty("关键词")
    private String noticeKeywords;
}