package org.jensen.mq.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/6 14:50
 * @Description: 生产者
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME = "queue_prefetch";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //声明队列，队列持久化
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //发送消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            //发送消息
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        }
        //关闭资源
        channel.close();
    }
}
