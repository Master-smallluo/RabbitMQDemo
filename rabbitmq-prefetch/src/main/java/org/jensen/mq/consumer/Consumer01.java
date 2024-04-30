package org.jensen.mq.consumer;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import org.jensen.mq.publisher.Producer;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/6 14:58
 * @Description: 消费者
 */
public class Consumer01 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //设置预取值
        channel.basicQos(2);
        //获取消息
        channel.basicConsume(Producer.QUEUE_NAME, false, (s, delivery) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println("消费者01收到消息：" + message);
                //手动确认消息
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }, s -> {
            System.out.println("消息被取消的回调函数" + s);
        });
    }
}
