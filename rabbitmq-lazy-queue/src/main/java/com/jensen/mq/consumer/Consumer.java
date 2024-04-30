package com.jensen.mq.consumer;

import com.jensen.mq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/13 13:51
 * @Description: Consumer 消费者
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //消费消息
        channel.basicConsume("queue_lazy", true, (s, delivery) -> {
            System.out.println("消费者收到消息：" + new String(delivery.getBody()));
        }, s -> {
            System.out.println("消费者取消了消息!");
        });
    }
}
