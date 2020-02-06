package com.ky.ulearning.monitor.controller;

import com.ky.ulearning.common.core.annotation.Log;
import com.ky.ulearning.common.core.annotation.PermissionName;
import com.ky.ulearning.common.core.api.controller.BaseController;
import com.ky.ulearning.common.core.component.component.FastDfsClientWrapper;
import com.ky.ulearning.common.core.constant.MicroConstant;
import com.ky.ulearning.common.core.message.JsonResult;
import com.ky.ulearning.common.core.utils.RequestHolderUtil;
import com.ky.ulearning.common.core.utils.ResponseEntityUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.common.core.validate.ValidatorBuilder;
import com.ky.ulearning.common.core.validate.handler.ValidateHandler;
import com.ky.ulearning.monitor.common.constants.MonitorManageErrorCodeEnum;
import com.ky.ulearning.monitor.service.FileRecordService;
import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.monitor.dto.FileRecordDto;
import com.ky.ulearning.spi.monitor.entity.FileRecordEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * 文件管理controller
 *
 * @author luyuhao
 * @since 20/02/06 17:06
 */
@Api(tags = "文件管理", description = "文件管理接口")
@RestController
@RequestMapping("/fileRecord")
public class FileRecordController extends BaseController {

    @Autowired
    private FileRecordService fileRecordService;

    @Autowired
    private FastDfsClientWrapper fastDfsClientWrapper;

    @ApiOperation(value = "", hidden = true)
    @PostMapping("/add")
    public void add(FileRecordDto fileRecordDto) {
        fileRecordService.insert(fileRecordDto);
    }

    @Log("文件记录查询")
    @ApiOperation(value = "文件记录查询", notes = "分页查询，支持多条件筛选，其中recordSize不为空时，查询>=recordSize的所有记录")
    @ApiOperationSupport(ignoreParameters = {"id", "recordUrl", "recordTableId"})
    @PermissionName(source = "fileRecord:pageList", name = "文件记录查询", group = "文件管理")
    @GetMapping("/pageList")
    public ResponseEntity<JsonResult<PageBean<FileRecordEntity>>> pageList(PageParam pageParam,
                                                                           FileRecordDto fileRecordDto) {
        PageBean<FileRecordEntity> pageBean = fileRecordService.pageFileRecordList(fileRecordDto, setPageParam(pageParam));
        return ResponseEntityUtil.ok(JsonResult.buildData(pageBean));
    }

    @Log("根据id查询文件记录")
    @ApiOperation(value = "根据id查询文件记录")
    @PermissionName(source = "fileRecord:getById", name = "根据id查询文件记录", group = "文件管理")
    @GetMapping("/getById")
    public ResponseEntity<JsonResult<FileRecordEntity>> getById(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), MonitorManageErrorCodeEnum.ID_CANNOT_BE_NULL);
        FileRecordEntity fileRecordEntity = fileRecordService.getById(id);
        return ResponseEntityUtil.ok(JsonResult.buildData(fileRecordEntity));
    }

    @Log("删除文件")
    @ApiOperation(value = "删除文件")
    @PermissionName(source = "fileRecord:delete", name = "删除文件", group = "文件管理")
    @GetMapping("/delete")
    public ResponseEntity<JsonResult> delete(Long id) {
        ValidateHandler.checkParameter(StringUtil.isEmpty(id), MonitorManageErrorCodeEnum.ID_CANNOT_BE_NULL);
        FileRecordEntity fileRecordEntity = fileRecordService.getById(id);
        ValidateHandler.checkParameter(fileRecordEntity == null, MonitorManageErrorCodeEnum.FILE_RECORD_NOT_EXISTS);
        fileRecordService.delete(id, RequestHolderUtil.getAttribute(MicroConstant.USERNAME, String.class));
        fastDfsClientWrapper.deleteFile(fileRecordEntity.getRecordUrl());
        return ResponseEntityUtil.ok(JsonResult.buildMsg("删除成功"));
    }

    @Log("下载文件")
    @ApiOperation(value = "下载文件")
    @PermissionName(source = "fileRecord:download", name = "下载文件", group = "文件管理")
    @GetMapping("/download")
    public ResponseEntity download(Long id) {
        ValidatorBuilder.build()
                .on(StringUtil.isEmpty(id), MonitorManageErrorCodeEnum.ID_CANNOT_BE_NULL)
                .doValidate().checkResult();
        //获取实验信息
        FileRecordEntity fileRecordEntity = fileRecordService.getById(id);
        ValidateHandler.checkParameter(fileRecordEntity == null, MonitorManageErrorCodeEnum.FILE_RECORD_NOT_EXISTS);
        //判断文件是否为空
        String fileUrl = fileRecordEntity.getRecordUrl();
        ValidateHandler.checkParameter(StringUtil.isEmpty(fileUrl), MonitorManageErrorCodeEnum.FILE_NOT_EXISTS);
        String attachmentName = StringUtil.isEmpty(fileRecordEntity.getRecordName()) ? "未命名" : fileRecordEntity.getRecordName();

        //下载并校验附件是否过期
        byte[] attachmentBytes = fastDfsClientWrapper.download(fileUrl);
        ValidateHandler.checkParameter(attachmentBytes == null, MonitorManageErrorCodeEnum.FILE_ILLEGAL);

        //设置head
        HttpHeaders headers = new HttpHeaders();
        //文件的属性名
        headers.setContentDispositionFormData("attachment", new String(attachmentName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        //响应内容是字节流
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntityUtil.ok(headers, attachmentBytes);
    }
}
