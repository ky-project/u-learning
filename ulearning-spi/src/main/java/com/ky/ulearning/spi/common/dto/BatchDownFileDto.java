package com.ky.ulearning.spi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 批量下载文件dto
 *
 * @author luyuhao
 * @date 2020/07/09 01:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchDownFileDto {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件流
     */
    private byte[] bytes;
}
