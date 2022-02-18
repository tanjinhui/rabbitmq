package org.rabbitmq.rabbitconfig;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicMqConfig {

    /**
     * exchange name
     */
    public static final String TOPIC_EXCHANGE_NAME = "topic_exchange";

    /**
     * routing key
     */
    public static final String TOPIC_BINGDING_KEY_TEST1 = "topic.kd.*";
    public static final String TOPIC_BINGDING_KEY_TEST2 = "topic.kd.jd";
    public static final String TOPIC_BINGDING_KEY_TEST3 = "topic.kd.zt";

    /**
     * queue
     */
    public static final String TOPIC_QUEUE_TEST1 = "topic_test1";
    public static final String TOPIC_QUEUE_TEST2 = "topic_test2";

    /**
     * TopicExchange
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
    }

    /**
     * new Queue
     *
     * @return
     */
    @Bean
    public Queue topic_test1Queue() {
        return new Queue(TOPIC_QUEUE_TEST1, true);
    }

    @Bean
    public Queue topic_test2Queue() {
        return new Queue(TOPIC_QUEUE_TEST2, true);
    }

    /**
     * binging
     *
     * @return
     */
    @Bean
    public Binding topic_test1Binding() {
        return BindingBuilder.bind(topic_test1Queue()).to(topicExchange()).with(TOPIC_BINGDING_KEY_TEST1);
    }

    /**
     * binging
     * @return
     */
    @Bean
    public Binding topic_test2Binding() {
        return BindingBuilder.bind(topic_test2Queue()).to(topicExchange()).with(TOPIC_BINGDING_KEY_TEST2);
    }

}
