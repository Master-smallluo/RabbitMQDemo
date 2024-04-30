package org.jensen.mq.confirm;

import com.rabbitmq.client.Channel;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/6 15:32
 * @Description: 单个确认
 */
public class SingleConfirm {
    //发消息的个数
    public static final int message_count = 1000 ;
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //队列声明
        String queue_name = UUID.randomUUID().toString();
        channel.queueDeclare(queue_name,true,false,false,null);
        //开启确认
        channel.confirmSelect();
        //开启时间
        long start = System.currentTimeMillis();

        //逐个发消息再确认
        for (int i = 0; i < message_count; i++) {
            String message = i + "";
            channel.basicPublish("",queue_name,null,message.getBytes());
            //每发完一条消息，就进行一次确认
            boolean flag = channel.waitForConfirms();
            if (flag){
                System.out.println("消息发送成功!");
            }
        }
        //结束时间
        long end = System.currentTimeMillis();
        System.out.println("发送1000条消息单个确认总耗时：" + (end - start) + "ms");
    }
}
