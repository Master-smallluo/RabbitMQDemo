package org.jensen.mq.consumer;

import com.rabbitmq.client.Channel;
import org.jensen.mq.publisher.Producer;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/6 12:01
 * @Description: 消费者采用不公平获取消息的方式
 *
 */
public class Consumer01 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //设置不公平获取，0->轮询方式 1->随机方式 1以上->预取值
        channel.basicQos(1);
        //获取消息
        channel.basicConsume(Producer.QUEUE_NAME, false, (s, delivery) -> {
            //当前线程睡1s
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("接收到的消息:" + new String(delivery.getBody(), StandardCharsets.UTF_8));
            //手动应答
            //消息的标记 tag
            //是否批量应答(批量应答容易造成消息丢失)
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }, s -> {
            System.out.println("消费者取消消息回调函数" + s);
        });
    }
}
