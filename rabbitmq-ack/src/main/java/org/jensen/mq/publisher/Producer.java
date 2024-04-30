package org.jensen.mq.publisher;

import com.rabbitmq.client.Channel;
import org.jensen.mq.utils.RabbitMQUtils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author: JenSen
 * @Date: 2023/11/6 10:31
 * @Description: 消息在手动应答时是不丢失的，放回队列中重新消费
 */
public class Producer {
    //队列名称
    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQUtils.getChannel();
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //从控制台中输入信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
