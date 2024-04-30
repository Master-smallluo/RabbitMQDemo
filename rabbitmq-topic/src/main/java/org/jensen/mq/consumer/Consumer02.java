package org.jensen.mq.consumer;

import com.rabbitmq.client.Channel;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/8 14:54
 * @Description: 消费者
 */
public class Consumer02 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //声明一个队列
        channel.queueDeclare("Q2",false,false,false,null);
        //声明一个主题交换机
        channel.exchangeDeclare("topic_exchange","topic");
        //绑定队列和交换机
        channel.queueBind("Q2","topic_exchange","*.*.rabbit");
        channel.queueBind("Q2","topic_exchange","lazy.#");
        //消费消息
        channel.basicConsume("Q2", true, (s, delivery) -> {
            System.out.println("消费者2收到消息：" + new String(delivery.getBody()));
        }, s -> {
            System.out.println("消费者2取消了消费");
        });
    }
}
