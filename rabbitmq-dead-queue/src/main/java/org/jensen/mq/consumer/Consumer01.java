package org.jensen.mq.consumer;

import com.rabbitmq.client.Channel;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/9 11:34
 * @Description: 消费者01
 */
public class Consumer01 {
    //普通交换机的名称
    public static final String EXCHANGE_NAME = "exchange_01";
    //死信交换机的名称
    public static final String DEAD_EXCHANGE_NAME = "exchange_dead_01";

    //普通队列的名称
    public static final String QUEUE_NAME = "queue_01";
    //死信队列的名称
    public static final String DEAD_QUEUE_NAME = "queue_dead_01";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        channel.exchangeDeclare(DEAD_EXCHANGE_NAME, "direct");
        //声明队列
        //普通队列将死信消息转发给死信队列
        //声明map
        HashMap<String, Object> arguments = new HashMap<>();
        //过期时间
//        arguments.put("x-message-ttl",100000);//一般不指定队列中的消息过期时间，由生产者确定
        //正常队列将死信消息转发给死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE_NAME);
        //设置死信队列RoutingKey
        arguments.put("x-dead-letter-routing-key","lisi");
        //设置正常队列长度的限制
//        arguments.put("x-max-length",6);

        channel.queueDeclare(QUEUE_NAME, false, false, false, arguments);

        channel.queueDeclare(DEAD_QUEUE_NAME, false, false, false, null);
        //绑定普通的队列和交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "zhangsan");
        //绑定死信队列和交换机
        channel.queueBind(DEAD_QUEUE_NAME, DEAD_EXCHANGE_NAME, "lisi");
        //消费消息
        channel.basicConsume(QUEUE_NAME, false, (s, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            if (message.equals("hello world5")){
                //消费者拒收消息，且不放回普通队列，就会将消息转发到死信队列
                System.out.println("消费者01拒收消息:" + message);
                channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
            }else{
                System.out.println("消费者01接收到消息:" + message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        }, System.out::println);
    }
}
