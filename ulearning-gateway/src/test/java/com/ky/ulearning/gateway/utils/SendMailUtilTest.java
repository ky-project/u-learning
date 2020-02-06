package com.ky.ulearning.gateway.utils;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import com.ky.ulearning.gateway.common.utils.SendMailUtil;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author luyuhao
 * @since 20/02/07 01:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SendMailUtilTest {

    @Autowired
    private SendMailUtil sendMailUtil;

    @BeforeClass
    public static void init(){
        EnvironmentAwareUtil.adjust();
    }

    @Test
    public void test01() throws InterruptedException {
        long now = System.currentTimeMillis();
        sendMailUtil.sendVerifyCodeMail("陆宇豪", "684224", "784121671@qq.com");
        System.out.println(new Date() + "=>" + (System.currentTimeMillis() - now));
        Thread.sleep(5000);
    }
}
