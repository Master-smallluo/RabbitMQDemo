package org.jensen.mq.consumer;


import com.rabbitmq.client.Channel;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/9 11:34
 * @Description: 消费者02
 */
public class Consumer02 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //消费消息
        channel.basicConsume(Consumer01.DEAD_QUEUE_NAME, true, (s, delivery) -> {
            System.out.println("消费者01接收到消息:"+new String(delivery.getBody(), StandardCharsets.UTF_8));
        }, System.out::println);
    }
}
