package org.jensen.mq.publisher;



import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @author: JenSen
 * @Date: 2023/11/5 22:16
 * @Description: 生产者
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME = "queue_hello";
    //创建连接工厂并且发送消息
    public static void main(String[] args) throws TimeoutException {
        //1.创建连接工厂
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
        //创建连接
        // 创建通道
        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
            //创建队列(如果没有)
            //队列名称
            //队列里面的消息是否持久化
            //队列是否自动删除
            //队列的其他属性
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //发送消息
            String message = "hello world"; //消息内容
            //交换机名称
            //路由键=>routing key
            //消息的其他属性
            //消息的字节数组
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
