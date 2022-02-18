package org.rabbitmq.controller;



import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.rabbitmq.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 确认到达exchange and queue
 */
@Slf4j
@Component
public class Exchange_queue_conform implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback
{

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Resource
    private RedisUtils redisUtil;
    
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
 
    }

    /**
     * 是否到达exchange
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
    	String id=correlationData.getId();
        if (ack) {
        	System.out.println(String.format("sent to exchange successfully,%s", correlationData));
        	redisUtil.set("confirm.id:"+id,"ok");
        	log.debug(String.format("sent to exchange successfully,%s", correlationData));
        } else {
            System.out.println(String.format("sent to exchange fail,%s", cause));
            boolean result=redisUtil.set("confirm.errorid:"+id,cause);
        	log.debug(String.format("send message fail,%s", cause));
        }
    }

    /**
     * 找不到queue
     * @param message
     */
    @Override
    public void returnedMessage(ReturnedMessage message)
    {
       String errorID=message.getMessage().getMessageProperties().getHeader("spring_returned_message_correlation");
       System.out.println(String.format("message to queue fail,%s", errorID));
       boolean result=redisUtil.set("return.errorid:"+errorID,message.getReplyText());
       System.out.println(String.format("error ID:,%s", errorID));
    	System.out.println(String.format("message:,%s", message.getMessage()));
    	System.out.println(String.format("replycode:", message.getReplyCode()));
    	System.out.println(String.format("replyText:,%s",message.getReplyText()));
    	System.out.println(String.format("exchange:%s",message.getExchange()));
    	System.out.println(String.format("routing:,%s",message.getRoutingKey()));

    }
}
