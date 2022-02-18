package org.rabbitmq.rabbitconfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class dle_exchange_queueConfig {

    /**
     * exchange name
     */
    public static final String DLE_EXCHANGE_NAME = "dle_exchange";

    /**
     * routing key
     */
    public static final String DLE_BINGDING_KEY_TEST1 = "dlk";

    /**
     * queue
     */
    public static final String DLE_QUEUE_TEST1 = "dle_test1";
    @Bean
    public DirectExchange DLE_Exchange()
    {
        return new DirectExchange(DLE_EXCHANGE_NAME, true, false);
    }

    /**
     * new Queue
     *
     * @return
     */
    @Bean
    public Queue dle_test1Queue() {
        return QueueBuilder.durable(DLE_QUEUE_TEST1).build();
    }
    @Bean
    public Binding dle_test1Binding() {
        return BindingBuilder.bind(dle_test1Queue()).to(DLE_Exchange()).with(DLE_BINGDING_KEY_TEST1);
    }
}
