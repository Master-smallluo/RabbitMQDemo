package org.jensen.mq.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/6 11:49
 * @Description: 队列消息持久化(消息还是有可能丢失)
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME = "durable_queue";
    //发送消息
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道对象
        Channel channel = RabbitMQUtils.getChannel();
        //声明队列,(只能在声明队列确认队列是否持久化,创建队列后就不能修改队列的持久化状态)
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //从控制台中输入信息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            //发送消息，并且设置消息的持久化(保存在磁盘上)
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送成功："+message);
        }
    }
}
