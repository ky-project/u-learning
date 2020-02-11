package com.ky.ulearning.common.core.utils;

import cn.hutool.core.util.IdUtil;
import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.ky.ulearning.common.core.constant.FileTypeEnum;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author luyuhao
 * @date 19/12/05 02:44
 */
public class FileUtil {

    /**
     * 定义GB的计算常量
     */
    private static final int GB = 1024 * 1024 * 1024;
    /**
     * 定义MB的计算常量
     */
    private static final int MB = 1024 * 1024;
    /**
     * 定义KB的计算常量
     */
    private static final int KB = 1024;

    /**
     * 格式化小数
     */
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    /**
     * 常见图片类型
     */
    public static final String[] IMAGE_TYPE = {"jpg", "png", "jpeg", "gif", "bmp"};

    /**
     * 常见附件类型
     */
    public static final String[] ATTACHMENT_TYPE = {
            //图片
            "jpg", "png", "jpeg", "gif", "bmp",
            //音频
            "mp4", "mp3", ".avi",
            //文档
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf",
            //压缩文件
            "zip", "rar"};

    /**
     * MultipartFile转File
     */
    public static File toFile(MultipartFile multipartFile) {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = "." + getExtensionName(fileName);
        File file = null;
        try {
            // 用uuid作为文件名，防止生成的临时文件重复
            file = File.createTempFile(IdUtil.simpleUUID(), prefix);
            // MultipartFile to File
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 删除文件
     */
    public static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 文件大小转换
     */
    public static String getSize(long size) {
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = DF.format(size / (float) GB) + "GB   ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = DF.format(size / (float) MB) + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = DF.format(size / (float) KB) + "KB   ";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }

    /**
     * inputStream 转 File
     */
    public static File inputStreamToFile(InputStream ins, String name) throws Exception {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + name);
        if (file.exists()) {
            return file;
        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }

    /**
     * 校验文件后缀是否符合类型规则
     *
     * @param multipartFile 文件
     * @param typeRules     类型规则
     */
    public static boolean fileTypeRuleCheck(MultipartFile multipartFile, String... typeRules) {
        if (StringUtil.isArrEmpty(typeRules)) {
            return true;
        }
        //获取文件后缀
        String fileExt = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        //后缀转大写
        String fileExtUpper = Optional.ofNullable(fileExt).map(String::toUpperCase).orElse("");
        //遍历比较
        for (String typeRule : typeRules) {
            if (typeRule.toUpperCase().equals(fileExtUpper)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验文件是否存在篡改
     *
     * @param multipartFile 待校验文件流
     */
    public static boolean fileTypeCheck(MultipartFile multipartFile) {
        try {
            boolean resFlag = false;
            //获取文件后缀
            String fileExt = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            //后缀转大写
            String fileExtUpper = Optional.ofNullable(fileExt).map(String::toUpperCase).orElse("");
            byte[] buffer = new byte[10];
            multipartFile.getInputStream().read(buffer);

            //获取当前文件的真实类型
            Set<String> currentFileType = getTrueFileType(bytesToHexFileTypeString(buffer));

            //指定文件类型中是否匹配当前文件类型
            if (currentFileType.contains(fileExtUpper)) {
                resFlag = true;
            }

            return resFlag;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将字节流转为hex文件格式字符串
     *
     * @param buffer 字节流
     * @return 文件类型string
     */
    private static String bytesToHexFileTypeString(byte[] buffer) {
        StringBuilder hexFileTypeStr = new StringBuilder();
        for (byte b : buffer) {
            String hexString = Integer.toHexString(b & 0xFF);
            if (hexString.length() < 2) {
                hexFileTypeStr.append("0");
            }
            hexFileTypeStr.append(hexString);
        }
        return hexFileTypeStr.toString();
    }

    /**
     * 根据文件流解析byte获取文件类型
     *
     * @param s 文件流字符串
     * @return 文件类型
     */
    private static Set<String> getTrueFileType(String s) {
        Set<String> typeList = new HashSet<>();
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (s.toUpperCase().startsWith(fileTypeEnum.getValue().toUpperCase())) {
                typeList.add(fileTypeEnum.toString().toUpperCase());
            }
        }
        return typeList;
    }

    /**
     * 测试main
     * xlsx: 504b0304140006000800
     * xls:  d0cf11e0a1b11ae10000
     * docx: 504b0304140006000800
     * doc:  d0cf11e0a1b11ae10000
     * mp3:  49443303000000000076
     * avi:  52494646c4dd5f004156
     * zip:  504b03040a0000000000
     * rar:  526172211a0700cf9073
     * pptx: 504b0304140006000800
     * ppt:  d0cf11e0a1b11ae10000
     */
    public static void main(String[] args) throws IOException {
        String filePath = "D:\\Mydata\\STU\\Java\\资料\\Java视频\\Java从入门到精通（第4版）（配光盘）9787302444541\\PPT\\第14章\\01 集合类概述.ppt";
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);

        MultipartFile multipartFile = new MockMultipartFile("a.html", file.getName(), "text/plain", IOUtils.toByteArray(input));
        byte[] buffer = new byte[10];
        multipartFile.getInputStream().read(buffer);

        //获取当前文件的流
        String typeString = bytesToHexFileTypeString(buffer);
        System.out.println(typeString);
    }
}
