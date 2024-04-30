package org.jensen.mq.confirm;

import com.rabbitmq.client.Channel;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.jensen.mq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @author: JenSen
 * @Date: 2023/11/6 15:36
 * @Description: 批量确认
 */
public class BatchConfirm {
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

        //批量确认数量
        final int batchSize = 100;

        //批量发消息再确认
        for (int i = 1; i <= message_count; i++) {
            String message = i + "";
            channel.basicPublish("",queue_name,null,message.getBytes());
            //判断达到100条消息 批量确认一次
            if (i % batchSize == 0){
                boolean b = channel.waitForConfirms();
                if (b){
                    System.out.println("消息确认成功!");
                }
            }
        }


        //结束时间
        long end = System.currentTimeMillis();
        System.out.println("发送1000条消息批量确认总耗时：" + (end - start) + "ms");
    }
}
