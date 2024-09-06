package com.laofan.iantha.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class RabbitMqConfig {

    private static final Logger log = LoggerFactory.getLogger(RabbitMqConfig.class);

    /**
     * TODO 请修改rabbitmq队列名字
     */
    public static final String QUEUE_PAY_NOTIFY = "pay_notify";
    /**
     * 交换机
     */
    public static final String TOPIC_EXCHANGE = "pay";

    /**
     * 路由key
     */
    public static final String ROUTING_KEY = "pay-center-key";

    /*** 定义交换机 * @return */
    @Bean
    public TopicExchange createTopicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * 定义回调消息队列
     */
    @Bean
    public Queue directOrderDicQueue() {
        log.info("创建队列：{{}}", QUEUE_PAY_NOTIFY);
        return new Queue(QUEUE_PAY_NOTIFY);
    }

    /*** 把队列 绑定到交换机里面指定的路由key * 解释: 将QUEUE_PAY_NOTIFY绑定到TOPIC_EXCHANGE里指定的ROUTING_KEY ** @return 绑定之后的一个关系 */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(directOrderDicQueue()).to(createTopicExchange()).with(ROUTING_KEY);
    }
}
