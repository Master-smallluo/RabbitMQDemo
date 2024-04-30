package org.jensen.mq.utils;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/5 23:15
 * @Description: RabbitMQ工具类
 */
public class RabbitMQUtils {
    //创建获取通道对象的方法
    public static Channel getChannel() throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //工厂配置信息
        connectionFactory.setHost("47.108.232.164");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("jensen");
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPassword("jy114033");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //返回通道
        return connection.createChannel();
    }
}
