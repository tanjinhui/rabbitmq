package org.rabbitmq.controller;

import java.io.IOException;
import org.rabbitmq.model.ordaddress;
import org.rabbitmq.redis.RedisUtils;
import org.rabbitmq.repository.ordaddressRespository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component

public class ReceiverMessage {
    private int max_attempts=3;
    @Autowired
    private RedisUtils redisutil;
    @Autowired
	private ordaddressRespository addrResp;
	@RabbitListener(queues ={"test2"})
    public void processHandler(String msg, Channel channel, Message message) throws IOException
    {
        try {
            log.info("jimmy receive message--{}", msg);
            String id=message.getMessageProperties().getHeader("spring_returned_message_correlation");
            ordaddress model=new ordaddress();
            model.setaddrID(id);
            model.setOrderID(id);
            model.setaddressdetail("广州市越秀区越华路珠江国际大厦3903A");
            model.settelephone("18664899432");
            model.setTruename("jimmy");
            addrResp.save(model);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }  catch (Exception e) {
            max_attempts--;
            if(max_attempts<1)
            {
                max_attempts=3;
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                return;
            }
            if (message.getMessageProperties().getRedelivered()) {
                log.error("received fail,then rejected..."+e.getMessage());
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            } else {
                log.error("renew to put queue...");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
            
        }
    }
}
