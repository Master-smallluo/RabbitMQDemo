package org.jensen.mq.confirm;

import com.rabbitmq.client.Channel;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/6 16:30
 * @Description: 异步确认(效率最高,最安全)
 */
public class AsyncConfirm {
    //发消息的个数
    public static final int message_count = 1000 ;
    public static void main(String[] args) throws IOException, TimeoutException{
        //获取通道
        Channel channel = RabbitMQUtils.getChannel();
        //队列声明
        String queue_name = UUID.randomUUID().toString();
        channel.queueDeclare(queue_name,true,false,false,null);
        //开启确认
        channel.confirmSelect();

        //线程安全有序的一个哈希表 适用于高并发的情况下
        //轻松的实现将序号与消息进行关联
        //轻松批量删除条目 只要给到序号
        //支持高并发(多线程)
        ConcurrentSkipListMap<Long, String > map = new ConcurrentSkipListMap<>();


        //开启时间
        long start = System.currentTimeMillis();

        //消息监听器 监听哪些消息成功了 或者 失败了
        //消息的标记
        //是否为批量确认
        channel.addConfirmListener((l, b) -> {
            //是否为批量的
            if (b){
                //获取已确认的消息序号
                ConcurrentNavigableMap<Long,String> confirmed = map.headMap(l);
                //删除确认成功的消息个数
                if (!confirmed.isEmpty()){
                    //删除已确认的消息
                    confirmed.clear();
                }
            }else {
                //删除单个已确认消息
                map.remove(l);
            }
            //消息确认成功回调函数
            System.out.println("消息确认成功!" + l);
        }, (l, b) -> {
            String message = map.get(l);
            //消息确认失败回调函数
            System.out.println("消息确认失败!" + "未确认的消息是:" + message + "未确认的消息tag:" + l);
        });

        //逐个发送消息
        for (int i = 0; i < message_count; i++) {
            String message = i + "";
            channel.basicPublish("",queue_name,null,message.getBytes());
            //此处记录下所有要发送的消息 消息的总和
            map.put(channel.getNextPublishSeqNo(),message);
        }

        //结束时间
        long end = System.currentTimeMillis();
        System.out.println("发送1000条消息异步确认总耗时：" + (end - start) + "ms");
    }
}
