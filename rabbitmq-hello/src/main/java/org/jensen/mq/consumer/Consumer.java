package org.jensen.mq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/5 22:33
 * @Description: 消费者
 */
public class Consumer {
    //队列名称
    public static final String QUEUE_NAME = "queue_hello";
    //接收消息
    public static void main(String[] args) {
       //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //2.设置连接工厂的属性
        connectionFactory.setHost("47.108.232.164");
        //端口
        connectionFactory.setPort(5672);
        //虚拟主机
        connectionFactory.setVirtualHost("/");
        //用户名和密码
        connectionFactory.setUsername("jensen");
        connectionFactory.setPassword("jy114033");
        try(//创建连接
            Connection connection = connectionFactory.newConnection();
            //创建通道
            Channel channel = connection.createChannel()) {
            //接收消息
            //队列名称
            //是否自动确认
            //消费者消费成功后回调函数
            //消费者取消回调函数
            channel.basicConsume(QUEUE_NAME, true, (s, delivery) -> {
                System.out.println(new String(delivery.getBody()));
                System.out.println("消息被成功消费时的回调函数");
            }, s -> {
                System.out.println("消息消费被中断时被调用");
                System.out.println(s);
            });
        }catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
