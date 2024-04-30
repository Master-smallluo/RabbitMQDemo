package org.jensen.mq.consumer;

import com.rabbitmq.client.Channel;
import org.jensen.mq.publisher.Producer;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/6 10:30
 * @Description: 消费者(消息不能被丢失,手动应答)
 */
public class Consumer01 {
    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建通道
        Channel channel = RabbitMQUtils.getChannel();
        //接收指定队列中消息,手动应答,实际应答时机字节确定
        channel.basicConsume(Producer.QUEUE_NAME, false, (consumerTag, message) -> {
            //当前线程睡1s
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("接收到的消息:" + new String(message.getBody(), StandardCharsets.UTF_8));
            //手动应答
            //消息的标记 tag
            //是否批量应答(批量应答容易造成消息丢失)
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);

        }, consumerTag -> {
            System.out.println("消费者取消消息回调函数" + consumerTag);
        });
    }
}
