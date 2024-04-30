package org.jensen.mq.publisher;

import com.rabbitmq.client.Channel;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/8 11:17
 * @Description: 生产者
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
//        channel.exchangeDeclare("exchange_fanout", "fanout");//交换机已在消费者中声明，可以省略
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNext()){
            String message = scanner.next();
            //向交换机发送消息
            //交换机名称
            //路由键
            //消息内容
            channel.basicPublish("exchange_fanout","",null,message.getBytes());
        }
    }
}
