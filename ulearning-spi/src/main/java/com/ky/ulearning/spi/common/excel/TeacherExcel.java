package com.ky.ulearning.spi.common.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * 教师excel基类
 *
 * @author luyuhao
 * @since 2020/3/23 20:15
 */
@Data
@HeadRowHeight(20)
public class TeacherExcel {

    /**
     * 工号
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "工号", index = 0)
    private String teaNumber;

    /**
     * 姓名
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "姓名", index = 1)
    private String teaName;

    /**
     * 性别
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "性别", index = 2)
    private String teaGender;

    /**
     * 部门
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "部门", index = 3)
    private String teaDept;

    /**
     * 职称
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "职称", index = 4)
    private String teaTitle;

    /**
     * 联系电话
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "联系电话", index = 5)
    private String teaPhone;

    /**
     * Email
     */
    @ColumnWidth(value = 15)
    @ExcelProperty(value = "邮箱", index = 6)
    private String teaEmail;

    /**
     * 导入的错误信息
     */
    @ExcelIgnore
    private String errorMsg;

}
