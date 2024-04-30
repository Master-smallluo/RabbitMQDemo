package com.jensen.mq.publisher;

import com.jensen.mq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/13 13:51
 * @Description: Producer 生产者
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //声明惰性队列
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-queue-mode", "lazy");
        channel.queueDeclare("lazy-queue", false, false, false, arguments);
        //发送消息
        for (int i = 0; i < 10; i++) {
            String msg = "Hello RabbitMQ " + i;
            channel.basicPublish("", "lazy-queue", null, msg.getBytes());
        }
    }
}
