package org.rabbitmq.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;


/**
 * ????????????????
 */
@Configuration
public class DirectMqConfig {
 
    /**
     * exchange name
     */
    public static final String DIRECT_EXCHANGE_NAME = "direct_exchange";
 
    /**
     * routing key name
     */
    public static final String BINGDING_KEY_TEST1 = "direct_key1";
    public static final String BINGDING_KEY_TEST2 = "direct_key2";
 
    /**
     * queue name
     */
    public static final String QUEUE_TEST1 = "test1";
    public static final String QUEUE_TEST2 = "test2";
 
    /**
     * DirectExchange
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, true, false);
    }
 
    /**
     * queue with arguments
     *
     * @return
     */
    @Bean
    public Queue test1Queue() {
        Map<String,Object> args=new HashMap<>();
        args.put("x-message-ttl",10000);
        args.put("x-dead-letter-exchange",dle_exchange_queueConfig.DLE_EXCHANGE_NAME);
        args.put("x-dead-letter-routing-key",dle_exchange_queueConfig.DLE_BINGDING_KEY_TEST1);
        return QueueBuilder.durable(QUEUE_TEST1).withArguments(args).build();
    }
 
    @Bean
    public Queue test2Queue() {
        return new Queue(QUEUE_TEST2, true);
    }
 
    /**
     * binding
     *
     * @return
     */
    @Bean
    public Binding test1Binding() {
        return BindingBuilder.bind(test1Queue()).to(directExchange()).with(BINGDING_KEY_TEST1);
    }

    /**
     * binding
     * @return
     */
    @Bean
    public Binding test2Binding() {
        return BindingBuilder.bind(test2Queue()).to(directExchange()).with(BINGDING_KEY_TEST2);
    }
 
    /**
     * connection
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    //@Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    /**
     * channel
     * @param connectionFactory
     * @return
     */
    @Bean
    public Channel channel(ConnectionFactory connectionFactory) {
        return connectionFactory.createConnection().createChannel(false);
    }
 
}

