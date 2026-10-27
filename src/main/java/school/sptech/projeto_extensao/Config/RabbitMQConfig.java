package school.sptech.projeto_extensao.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "calendar.events.queue";
    public static final String EXCHANGE = "calendar.exchange";
    public static final String ROUTING_KEY = "calendar.event.create";
    public static final String ROUTING_KEY_UPDATE = "calendar.event.update";
    public static final String ROUTING_KEY_DELETE = "calendar.event.delete";

    @Bean
    public Queue calendarQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public TopicExchange calendarExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public Binding bindingAtualizacao(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding bindingExclusao(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_DELETE);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
