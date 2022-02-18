package org.rabbitmq.rabbitconfig;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutMqConfig {

    /**
     * exchange name
     */
    public static final String FANOUT_EXCHANGE_NAME = "fanout_exchange";

    /**
     * queue name
     */
    public static final String FANOUT_QUEUE_TEST1 = "fan_test1";
    public static final String FANOUT_QUEUE_TEST2 = "fan_test2";

    /**
     * FanoutExchange
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME, true, false);
    }

    /**
     * queue
     *
     * @return
     */
    @Bean
    public Queue fanout_test1Queue() {
        return new Queue(FANOUT_QUEUE_TEST1, true);
    }

    @Bean
    public Queue fanout_test2Queue() {
        return new Queue(FANOUT_QUEUE_TEST2, true);
    }

    /**
     * Binding
     *
     * @return
     */
    @Bean
    public Binding fanout_test1Binding() {
        return BindingBuilder.bind(fanout_test1Queue()).to(fanoutExchange());
    }

    @Bean
    public Binding fanout_test2Binding() {
        return BindingBuilder.bind(fanout_test2Queue()).to(fanoutExchange());
    }

}
