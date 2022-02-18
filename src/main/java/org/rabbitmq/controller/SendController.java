package org.rabbitmq.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.rabbitmq.rabbitconfig.DirectMqConfig;
import org.rabbitmq.rabbitconfig.FanoutMqConfig;
import org.rabbitmq.rabbitconfig.TopicMqConfig;
import org.rabbitmq.rabbitconfig.workMqConfig;
import org.rabbitmq.redis.RedisUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;

@RestController
public class SendController {
	@Autowired
    private RabbitTemplate rabbitTemplate;
	@Resource
    private RedisUtils redisUtil;
    @GetMapping("/send/direct")
    public void send_direct(HttpServletResponse response,HttpServletRequest request)  throws Exception
    {
    	String id=UUID.randomUUID().toString();
        CorrelationData data = new CorrelationData(id);
        Map<String,String> map=new HashMap<>();
        map.put("queueType","direct");
            Message message= MessageBuilder.withBody(JSON.toJSONString(map).getBytes("UTF-8")).setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).setExpiration("10000").build();
            long num=redisUtil.decr("a", 1);
            if(num>=0)
            {
            	rabbitTemplate.convertAndSend(DirectMqConfig.DIRECT_EXCHANGE_NAME,DirectMqConfig.BINGDING_KEY_TEST2,message,data);
                response.sendRedirect(request.getContextPath() +"/add?id="+id);	
            }
            else
            {
            	response.sendRedirect(request.getContextPath() +"/orderfail");	
            }
    }
    @GetMapping("/send/fanout")
    public void send_fanout(HttpServletResponse response,HttpServletRequest request)  throws Exception{
        String id=UUID.randomUUID().toString();
        CorrelationData data = new CorrelationData(id);
        Map<String,String> map=new HashMap<>();
        map.put("queueType","fanout");
        Message message= MessageBuilder.withBody(JSON.toJSONString(map).getBytes("UTF-8")).setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build();
        long num=redisUtil.decr("a", 1);
        if(num>=0)
        {
            rabbitTemplate.convertAndSend(FanoutMqConfig.FANOUT_EXCHANGE_NAME,"",message,data);
            response.sendRedirect(request.getContextPath() +"/add?id="+id);
        }
        else
        {
            response.sendRedirect(request.getContextPath() +"/orderfail");
        }
    }
    @GetMapping("/send/topic")
    public void send_topic(HttpServletResponse response,HttpServletRequest request)  throws Exception
    {
        String id=UUID.randomUUID().toString();
        CorrelationData data = new CorrelationData(id);
        Map<String,String> map=new HashMap<>();
        map.put("queueType","topic");
        Message message= MessageBuilder.withBody(JSON.toJSONString(map).getBytes("UTF-8")).setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build();
        long num=redisUtil.decr("a", 1);
        if(num>=0)
        {
            rabbitTemplate.convertAndSend(TopicMqConfig.TOPIC_EXCHANGE_NAME,TopicMqConfig.TOPIC_BINGDING_KEY_TEST3,message,data);
            response.sendRedirect(request.getContextPath() +"/add?id="+id);
        }
        else
        {
            response.sendRedirect(request.getContextPath() +"/orderfail");
        }
    }
    @GetMapping("/send/work")
    public void send_work(HttpServletResponse response,HttpServletRequest request)  throws Exception
    {
        String id=UUID.randomUUID().toString();
        CorrelationData data = new CorrelationData(id);
        Map<String,String> map=new HashMap<>();
        map.put("queueType","work");
        Message message= MessageBuilder.withBody(JSON.toJSONString(map).getBytes("UTF-8")).setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build();
        long num=redisUtil.decr("a", 1);
        if(num>=0)
        {
            rabbitTemplate.convertAndSend("", workMqConfig.WORK_QUEUE_TEST1,message,data);
            response.sendRedirect(request.getContextPath() +"/add?id="+id);
        }
        else
        {
            response.sendRedirect(request.getContextPath() +"/orderfail");
        }
    }
    @GetMapping("/sendError")
    public void sendError()
    {
        CorrelationData data = new CorrelationData(UUID.randomUUID().toString());
        Map<String,String> map=new HashMap<>();
        map.put("error queue","test");
        try {
            Message message= MessageBuilder.withBody(JSON.toJSONString(map).getBytes("UTF-8")).setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN).build();
            rabbitTemplate.convertAndSend(DirectMqConfig.DIRECT_EXCHANGE_NAME,"test",message,data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
 
    }
}
