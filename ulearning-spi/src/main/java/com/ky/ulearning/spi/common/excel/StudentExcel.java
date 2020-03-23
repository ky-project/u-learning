package com.ky.ulearning.spi.common.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * 学生excel基类
 *
 * @author luyuhao
 * @since 2020/3/23 15:10
 */
@Data
@HeadRowHeight(20)
public class StudentExcel {

    /**
     * 学号
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "学号", index = 0)
    private String stuNumber;

    /**
     * 姓名
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "姓名", index = 1)
    private String stuName;

    /**
     * 性别
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "性别", index = 2)
    private String stuGender;

    /**
     * 系部
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "系部", index = 3)
    private String stuDept;

    /**
     * 班级
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "班级", index = 4)
    private String stuClass;

    /**
     * 联系电话
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "联系电话", index = 5)
    private String stuPhone;

    /**
     * Email
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "邮箱", index = 6)
    private String stuEmail;

    /**
     * 导入的错误信息
     */
    @ExcelIgnore
    private String errorMsg;
}
