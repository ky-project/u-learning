package com.ky.ulearning.common.core.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ky.ulearning.common.core.constant.CommonErrorCodeEnum;
import com.ky.ulearning.common.core.exceptions.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * excel工具类
 *
 * @author luyuhao
 * @since 2020/03/23 01:55
 */
@Slf4j
public class ExcelUtil {

    /**
     * 读取Excel文件，并根据实现的listener进行处理
     *
     * @param inputStream 输入流
     * @param excelModel  数据类型
     * @param listener    重新的listener
     */
    public static void readExcelToList(Class excelModel, InputStream inputStream, AnalysisEventListener listener) {
        EasyExcel.read(inputStream, excelModel, listener)
                .sheet().doRead();
    }

    /**
     * 根据clazz中添加@ExcelProperty等注解的excel类，生成excel模板
     *
     * @param excelModel 带有excel注解的excel模型类的类型
     * @return byte[]
     */
    public static byte[] createTemplate(Class excelModel) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            EasyExcel.write(out, excelModel)
                    .sheet("模板")
                    .doWrite(Collections.emptyList());
            return out.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException(CommonErrorCodeEnum.CREATE_TEMPLATE_ERROR);
        } finally {
            if (Objects.nonNull(out)) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 根据clazz中添加@ExcelProperty等注解的excel类，导出excel
     *
     * @param excelModel 带有excel注解的excel模型类的类型
     * @param data       数据
     * @param sheetName  sheet名
     * @return byte[]
     */
    public static <T> byte[] export(Class excelModel, List<T> data, String sheetName) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            EasyExcel.write(out, excelModel)
                    .sheet(sheetName)
                    .doWrite(data);
            return out.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException(CommonErrorCodeEnum.CREATE_TEMPLATE_ERROR);
        } finally {
            if (Objects.nonNull(out)) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
