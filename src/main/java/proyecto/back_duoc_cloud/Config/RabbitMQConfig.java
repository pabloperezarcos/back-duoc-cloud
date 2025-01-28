/* 
 * Este archivo define los componentes necesarios para la integración con RabbitMQ: 
 * cola, intercambio, clave de enrutamiento, etc.
 */
package proyecto.back_duoc_cloud.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String QUEUE_ALERTAS = "queues_alertasmedicas";
    public static final String EXCHANGE_ALERTAS = "exchanges_alertasmedicas";
    public static final String ROUTING_KEY = "alertas_routing_key";

    @Bean
    public Queue alertaQueue() {
        return new Queue(QUEUE_ALERTAS, true); // Cola durable
    }

    @Bean
    public Exchange alertaExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_ALERTAS).durable(true).build();
    }

    @Bean
    public Binding binding(Queue alertaQueue, Exchange alertaExchange) {
        return BindingBuilder.bind(alertaQueue).to(alertaExchange).with(ROUTING_KEY).noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
