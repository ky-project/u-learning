package com.ky.ulearning.common.core.component.component;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * fastDFS封装工具类
 *
 * @author luyuhao
 * @since 20/01/28 19:02
 */
@Slf4j
@Component
public class FastDfsClientWrapper {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return 文件访问地址
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);
    }

    /**
     * 将一段字符串生成一个文件上传
     *
     * @param content       文件内容
     * @param fileExtension 文件后缀名
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = fastFileStorageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(storePath);
    }

    /**
     * 封装图片完整URL地址
     */
    private String getResAccessUrl(StorePath storePath) {
        return "http://" + defaultConfigParameters.getFdsReqHost()
                + ":" + defaultConfigParameters.getDfsReqPort() + "/" + storePath.getFullPath();
    }

    /**
     * 下载文件
     *
     * @param fileUrl 文件url
     */
    public byte[] download(String fileUrl) {
        if(StringUtil.isEmpty(fileUrl)){
            return null;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            return fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     */
    public boolean deleteFile(String fileUrl) {
        if(StringUtil.isEmpty(fileUrl)){
            return true;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
