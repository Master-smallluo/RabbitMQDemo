package org.jensen.mq.publisher;

import com.rabbitmq.client.Channel;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/8 14:08
 * @Description: 生产者
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //发送消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String msg = scanner.next();
            //参数1：交换机名称
            //参数2：路由键
            //参数3：消息属性
            //参数4：消息内容
            //参数5：消息内容的字节数组
            //只让console队列接收消息
//            channel.basicPublish("direct_exchange","warn",null,msg.getBytes(StandardCharsets.UTF_8));
            //只让disk队列接收消息
            channel.basicPublish("direct_exchange","error",null,msg.getBytes(StandardCharsets.UTF_8));
        }
    }
}
