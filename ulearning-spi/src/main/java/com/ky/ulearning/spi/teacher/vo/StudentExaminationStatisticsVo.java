package com.ky.ulearning.spi.teacher.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生测试统计vo
 *
 * @author luyuhao
 * @since 2020/03/19 23:40
 */
@Data
@ApiModel("学生测试统计vo")
public class StudentExaminationStatisticsVo {

    /**
     * 测试总分
     */
    @ApiModelProperty("测试总分")
    public Double examinationTotalScore;

    /**
     * 最高分
     */
    @ApiModelProperty("最高分")
    private Double highestScore;

    /**
     * 最低分
     */
    @ApiModelProperty("最低分")
    private Double lowestScore;

    /**
     * 平均分
     */
    @ApiModelProperty("平均分")
    private Double averageScore;

    /**
     * 学生总人数
     */
    @ApiModelProperty("学生总人数")
    private Integer totalStudent;

    /**
     * 统计结果集合
     */
    @ApiModelProperty("统计结果集合")
    private List<StatisticalResultsVo> statisticalResultsList;

    /**
     * 考试状态统计集合
     */
    @ApiModelProperty("考试状态统计集合")
    private List<ExaminationStatusVo> examinationStatusList;

    public StudentExaminationStatisticsVo() {
        this.highestScore = 0.0;
        this.lowestScore = 0.0;
        this.averageScore = 0.0;
        this.totalStudent = 0;
        this.examinationStatusList = new ArrayList<>();
        this.statisticalResultsList = new ArrayList<>();
        this.statisticalResultsList.add(new StatisticalResultsVo(1, 0));
        this.statisticalResultsList.add(new StatisticalResultsVo(2, 0));
        this.statisticalResultsList.add(new StatisticalResultsVo(3, 0));
        this.statisticalResultsList.add(new StatisticalResultsVo(4, 0));
    }

    /**
     * 结果类型对应人数 + 1
     *
     * @param resultType 结果类型
     */
    public void addStatistical(Integer resultType) {
        for (StatisticalResultsVo statisticalResultsVo : this.statisticalResultsList) {
            if (resultType.equals(statisticalResultsVo.getResultType())) {
                statisticalResultsVo.setStuNumber(statisticalResultsVo.getStuNumber() + 1);
            }
        }
    }

}
