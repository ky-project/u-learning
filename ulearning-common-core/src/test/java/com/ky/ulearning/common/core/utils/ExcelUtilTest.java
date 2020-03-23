package com.ky.ulearning.common.core.utils;

import cn.hutool.core.io.FileUtil;
import com.ky.ulearning.spi.common.excel.StudentExcel;
import org.junit.Test;

import java.io.*;

/**
 * @author luyuhao
 * @since 2020/3/23 16:44
 */
public class ExcelUtilTest {

    @Test
    public void test01() {
        byte[] template = ExcelUtil.createTemplate(StudentExcel.class);
        System.out.println(template.length);
        File file = new File("D:\\template.xlsx");
        FileUtil.touch(file);
        FileUtil.writeFromStream(new ByteArrayInputStream(template), file);
    }

}
