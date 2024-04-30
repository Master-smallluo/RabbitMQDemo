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
 * @Date: 2023/11/8 11:17
 * @Description: 消费者
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //声明交换机
        //交换机的名称
        //交换机类型
        channel.exchangeDeclare("exchange_fanout", "fanout");
        //声明一个临时队列(名称)
        String queueName = channel.queueDeclare().getQueue();
        //临时队列绑定交换机
        //临时队列名称
        //交换机名称
        //路由键
        channel.queueBind(queueName, "exchange_fanout", "");

        //消费消息
        channel.basicConsume(queueName, true, (s, delivery) -> {
            System.out.println("消费者02收到消息：" + new String(delivery.getBody()));
        }, s -> {
            System.out.println("消费者02取消了消息");
        });
    }
}
