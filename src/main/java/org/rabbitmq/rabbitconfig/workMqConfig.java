package org.rabbitmq.rabbitconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class workMqConfig {


    /**
     * queue
     */
    public static final String WORK_QUEUE_TEST1 = "work_queue1";
    public static final String WORK_QUEUE_TEST2 = "work_queue2";

    /**
     * new Queue
     *
     * @return
     */
    @Bean
    public Queue work_test1Queue() {
        return new Queue(WORK_QUEUE_TEST1, true);
    }

    @Bean
    public Queue work_test2Queue() {
        return new Queue(WORK_QUEUE_TEST2, true);
    }

}
