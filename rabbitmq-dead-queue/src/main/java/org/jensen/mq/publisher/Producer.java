package org.jensen.mq.publisher;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.jensen.mq.consumer.Consumer01;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/9 11:34
 * @Description: 生产者
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //发送死信消息 设置ttl时间
//        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
//               .expiration("10000")
//               .build();
        for (int i = 1; i <11 ; i++) {
            String message = "hello world" + i;
            channel.basicPublish(Consumer01.EXCHANGE_NAME, "zhangsan", null, message.getBytes());
        }
    }
}
