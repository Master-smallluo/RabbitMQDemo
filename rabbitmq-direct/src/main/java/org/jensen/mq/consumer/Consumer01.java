package org.jensen.mq.consumer;

import com.rabbitmq.client.Channel;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/8 14:07
 * @Description: 消费者
 */
public class Consumer01 {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //声明direct交换机
        channel.exchangeDeclare("direct_exchange", "direct");
        //声明一个队列
        //参数1：队列名称
        //参数2：是否持久化
        //参数3：是否是独占队列
        //参数4：是否自动删除
        //参数5：队列其他属性
        channel.queueDeclare("console", false, false, false, null);
        //临时队列绑定交换机
        //参数1：队列名称
        //参数2：交换机名称
        //参数3：路由键
        channel.queueBind("console", "direct_exchange", "info");
        channel.queueBind("console", "direct_exchange", "warn");

        //消息消费
        channel.basicConsume("console", true, (consumerTag, message) -> {
            System.out.println("消费者1收到消息：" + new String(message.getBody()));
        }, consumerTag -> {
            System.out.println("消费者1取消消息");
        });
    }
}
