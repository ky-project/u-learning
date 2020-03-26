package com.ky.ulearning.system.common.rocketmq;

import com.alibaba.fastjson.JSON;
import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import com.ky.ulearning.common.core.utils.JsonUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author luyuhao
 * @since 2020/3/25 20:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RocketMQTest {

    @BeforeClass
    public static void init() {
        EnvironmentAwareUtil.adjust();
    }

    @Test
    public void test02() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        producer.setNamesrvAddr("47.95.14.126:9876");
        producer.setVipChannelEnabled(false);
        producer.start();
        try {
            Message message = new Message("log-topic", "user-tag", JSON.toJSONString("hello world").getBytes());
            System.out.println("生产者发送消息:" + JSON.toJSONString("hello world"));
            producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }

    @Test
    public void test03() throws MQClientException, InterruptedException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-group");

        consumer.setNamesrvAddr("47.95.14.126:9876");
        consumer.subscribe("log-topic", "user-tag");
        consumer.setVipChannelEnabled(false);

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(context);
                for (MessageExt msg : msgs) {
                    System.out.println("消费者消费数据:"+new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        Thread.sleep(10000);
        consumer.shutdown();
    }
}
