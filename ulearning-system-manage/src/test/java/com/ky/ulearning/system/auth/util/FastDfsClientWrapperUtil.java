package com.ky.ulearning.system.auth.util;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.ky.ulearning.common.core.component.constant.DefaultConfigParameters;
import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author luyuhao
 * @since 20/01/28 22:32
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FastDfsClientWrapperUtil {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private DefaultConfigParameters defaultConfigParameters;

    @BeforeClass
    public static void init(){
        EnvironmentAwareUtil.adjust();
    }

    @Test
    public void test01() throws FileNotFoundException {
        File file = new File("D://Mydata//Picture//素材//头像1.png");
        String fileName = file.getName();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        FileInputStream inputStream = new FileInputStream(file);

        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(), ext, null);

        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getPath());
        System.out.println(storePath.getGroup());
    }

    @Test
    public void test02(){
        StorePath filePath = StorePath.parseFromUrl("http://darren1112.com:8888/group1/M00/00/00/L18Ofl4wSgOAT5hpJ9CC9TuAQlg3071.sh");
        fastFileStorageClient.deleteFile(filePath.getGroup(), filePath.getPath());
    }
}
