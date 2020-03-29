package com.ky.ulearning.spi.common.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 学生测试结果excel
 *
 * @author luyuhao
 * @since 2020/03/29 17:10
 */
@Data
@HeadRowHeight(20)
public class StudentExaminationResultExcel {

    /**
     * 学生测试id
     */
    @ExcelIgnore
    private Long examiningId;

    /**
     * 测试任务名称
     */
    @ColumnWidth(value = 25)
    @ExcelProperty(value = "测试任务名称", index = 0)
    private String examinationName;

    /**
     * 学号
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "学号", index = 1)
    private String stuNumber;

    /**
     * 姓名
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "姓名", index = 2)
    private String stuName;

    /**
     * 系部
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "系部", index = 3)
    private String stuDept;

    /**
     * 成绩
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "成绩", index = 4)
    private Double stuScore;

    /**
     * 准确率
     */
    @ExcelIgnore
    private Double accuracy;

    /**
     * 准确率
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "准确率", index = 5)
    private String accuracyStr;

    /**
     * 排名
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "排名", index = 6)
    private Integer ranking;

    /**
     * 提交时间
     */
    @ColumnWidth(value = 25)
    @ExcelProperty(value = "提交时间", index = 7)
    private Date examiningStateSwitchTime;
}
