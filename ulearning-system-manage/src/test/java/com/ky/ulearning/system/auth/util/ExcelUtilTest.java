package com.ky.ulearning.system.auth.util;

import com.ky.ulearning.common.core.utils.ExcelUtil;
import com.ky.ulearning.spi.common.excel.StudentExcel;
import com.ky.ulearning.spi.system.entity.StudentEntity;
import com.ky.ulearning.system.common.excel.StudentExcelListener;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author luyuhao
 * @since 2020/3/23 19:21
 */
public class ExcelUtilTest {

    @Test
    public void test02() throws FileNotFoundException {
        File file = new File("D:\\template.xlsx");
        InputStream inputStream = new FileInputStream(file);
        StudentExcelListener listener = new StudentExcelListener(null);
        ExcelUtil.readExcelToList(StudentExcel.class, inputStream, listener);
        Map<Integer, StudentExcel> errorMap = listener.getErrorMap();
        if (!CollectionUtils.isEmpty(errorMap)) {
            for (Map.Entry<Integer, StudentExcel> entry : errorMap.entrySet()) {
                System.out.println(entry.getKey() + "->" + entry.getValue());
            }
        }
    }
}
