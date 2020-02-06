package com.ky.ulearning.common.core.api.controller;

import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 基础controller类，包含controller通用方法
 *
 * @author luyuhao
 * @since 20/01/26 16:14
 */
public class BaseController {

    protected PageParam setPageParam(PageParam pageParam) {
        if (pageParam.getCurrentPage() != null && pageParam.getPageSize() != null) {
            pageParam.setStartIndex((pageParam.getCurrentPage() - 1) * pageParam.getPageSize());
        }
        return pageParam;
    }

    protected Map<String, Object> getFileRecordDto(String recordUrl, MultipartFile multipartFile,
                                                   String recordTable, Long recordTableId,
                                                   String username) {
        FileRecordDto fileRecordDto = new FileRecordDto();
        fileRecordDto.setRecordUrl(recordUrl);
        fileRecordDto.setRecordName(multipartFile.getOriginalFilename());
        fileRecordDto.setRecordSize(multipartFile.getSize());
        fileRecordDto.setRecordType(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        fileRecordDto.setRecordTable(recordTable);
        fileRecordDto.setRecordTableId(recordTableId);
        fileRecordDto.setUpdateBy(username);
        fileRecordDto.setCreateBy(username);
        return JsonUtil.parseObjectToMap(fileRecordDto);

    }
}
