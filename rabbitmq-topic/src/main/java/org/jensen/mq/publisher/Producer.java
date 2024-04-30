package org.jensen.mq.publisher;

import com.rabbitmq.client.Channel;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/8 15:03
 * @Description: 生产者
 */
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //发送消息
        Map<String, String> bindingKeyMap = new HashMap<>();
        bindingKeyMap.put("quick.orange.rabbit", "被队列Q1Q2接收到");
        bindingKeyMap.put("lazy.orange.elephant","被队列Q1Q2接收到");
        bindingKeyMap.put("quick.orange.fox","被队列Q1接收到");
        bindingKeyMap.put("lazy.brown.fox","被队列Q2接收到");
        bindingKeyMap.put("lazy.pink.rabbit","虽然满足两个绑定但只被队列Q2接收一次");
        bindingKeyMap.put("quick.brown.fox","不匹配任何绑定不会被任何队列接收到会被丢弃");
        bindingKeyMap.put("quick.orange..male.rabbit","是四个单词不匹配任何绑定会被丢弃");
        bindingKeyMap.put("lazy.orange.male.rabbit","是四个单词但匹配Q2");
        //遍历map
        for (Map.Entry<String, String> entry : bindingKeyMap.entrySet()) {
            channel.basicPublish("topic_exchange", entry.getKey(), null, entry.getValue().getBytes());
        }
    }
}
