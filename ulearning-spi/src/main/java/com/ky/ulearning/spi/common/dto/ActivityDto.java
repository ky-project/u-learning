package com.ky.ulearning.spi.common.dto;

import com.ky.ulearning.spi.common.entity.ActivityEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 动态dto {@link ActivityEntity}
 *
 * @author luyuhao
 * @since 2020/03/31 23:59
 */
@ApiModel("动态dto")
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityDto extends BaseDto {

    /**
     * 未查阅的学生/教师id 逗号分隔
     */
    @ApiModelProperty("未查阅的学生/教师id 逗号分隔")
    private String userIds;

    /**
     * 动态主题
     */
    @ApiModelProperty("动态主题")
    private String activityTopic;

    /**
     * 动态内容
     */
    @ApiModelProperty("动态内容")
    private String activityContent;

    /**
     * 动态类型 1：教师 2：学生
     */
    @ApiModelProperty("动态类型 1：教师 2：学生")
    private Integer activityType;

    /**
     * 推送邮箱
     */
    @ApiModelProperty("推送邮箱")
    private String activityEmail;
}
