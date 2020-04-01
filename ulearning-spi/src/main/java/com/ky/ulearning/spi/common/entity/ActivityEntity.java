package com.ky.ulearning.spi.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 动态实体类
 */
@ApiModel("动态实体类")
@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityEntity extends BaseEntity {

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

    public static ActivityEntity build(String createBy, String updateBy,
                                       String userIds, String activityTopic,
                                       String activityContent, Integer activityType,
                                       String activityEmail) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setUserIds(userIds);
        activityEntity.setActivityEmail(activityEmail);
        activityEntity.setUpdateBy(updateBy);
        activityEntity.setCreateBy(createBy);
        activityEntity.setActivityType(activityType);
        activityEntity.setActivityTopic(activityTopic);
        activityEntity.setActivityContent(activityContent);
        return activityEntity;
    }
}
