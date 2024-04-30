package org.jensen.mq.consumer;

import com.rabbitmq.client.Channel;
import org.jensen.mq.QueueName;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/5 23:21
 * @Description: 消费者01
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        //消费消息
        channel.basicConsume(QueueName.QUEUE_NAME, true, (s, delivery) -> {
            System.out.println("消费者02收到消息：" + new String(delivery.getBody()));
        }, s -> {
            System.out.println("消费者02取消了消息：" + s);
        });
    }
}
