package br.edu.utfpr.irigation.messageService;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "myQueue";
    public static final String EXCHANGE_NAME = "myExchange";

    // Configuração para logs de irrigação
    public static final String IRRIGATION_LOG_QUEUE = "irrigation.logs.queue";
    public static final String IRRIGATION_LOG_EXCHANGE = "irrigation.logs.exchange";
    public static final String IRRIGATION_LOG_ROUTING_KEY = "irrigation.logs.critical";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routing.key.#");
    }

    // Beans para fila de logs de irrigação
    @Bean
    public Queue irrigationLogQueue() {
        return new Queue(IRRIGATION_LOG_QUEUE, true); // durable = true
    }

    @Bean
    public TopicExchange irrigationLogExchange() {
        return new TopicExchange(IRRIGATION_LOG_EXCHANGE);
    }

    @Bean
    public Binding irrigationLogBinding(Queue irrigationLogQueue, TopicExchange irrigationLogExchange) {
        return BindingBuilder.bind(irrigationLogQueue).to(irrigationLogExchange).with(IRRIGATION_LOG_ROUTING_KEY);
    }
}