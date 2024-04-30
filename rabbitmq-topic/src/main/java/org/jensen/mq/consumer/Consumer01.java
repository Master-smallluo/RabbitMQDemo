package org.jensen.mq.consumer;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/8 14:54
 * @Description: 消费者
 */
public class Consumer01 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //声明一个队列
        channel.queueDeclare("Q1",false,false,false,null);
        //声明一个主题交换机
        channel.exchangeDeclare("topic_exchange","topic");
        //绑定队列和交换机
        channel.queueBind("Q1","topic_exchange","*.orange.*");

        //消费消息
        channel.basicConsume("Q1", true, (s, delivery) -> {
            System.out.println("消费者1收到消息：" + new String(delivery.getBody()));
        }, s -> {
                System.out.println("消费者1取消了消费");
        });
    }
}
