package com.ky.ulearning.system.common.rocketmq;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

/**
 * @author luyuhao
 * @since 2020/3/25 20:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RocketMQTest {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @BeforeClass
    public static void init(){
        EnvironmentAwareUtil.adjust();
    }

    @Test
    public void test01() throws InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
        Message msg = new Message("TopicTest", "tags1", "你好".getBytes(RemotingHelper.DEFAULT_CHARSET));
        // 发送消息到一个Broker
        SendResult sendResult = defaultMQProducer.send(msg);
        // 通过sendResult返回消息是否成功送达
        System.out.printf("%s%n", sendResult);

        Thread.sleep(10000);
    }
}
