package org.jensen.mq.publisher;

import com.rabbitmq.client.Channel;
import org.jensen.mq.QueueName;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/5 23:37
 * @Description: 生产者
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道对象
        Channel channel = RabbitMQUtils.getChannel();
        //从控制台读取数据并发送
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("", QueueName.QUEUE_NAME,null, message.getBytes());
        }
        channel.close();
    }
}
