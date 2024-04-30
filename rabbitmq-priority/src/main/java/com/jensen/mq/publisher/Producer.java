package com.jensen.mq.publisher;

import com.jensen.mq.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.sun.org.apache.bcel.internal.generic.I2F;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/13 13:03
 * @Description: Producer 生产者
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority", 10);
        //声明队列
        channel.queueDeclare("priority_queue", true, false, false, arguments);
        AMQP.BasicProperties properties = null;
        for (int i = 0; i < 10; i++) {
            //声明优先级
            if (i == 5){
                properties = new AMQP.BasicProperties.Builder()
                        .priority(i).build();
            }
            //发送消息
            channel.basicPublish("", "priority_queue", properties, ("hello world" + i).getBytes());
            properties = null;
        }
    }
}
