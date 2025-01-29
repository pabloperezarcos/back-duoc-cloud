/* 
 * Este archivo define los componentes necesarios para la integración con RabbitMQ: 
 * cola, intercambio, clave de enrutamiento, etc.
 */
package proyecto.back_duoc_cloud.Config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // Cola, Exchange y Routing Key para alertas médicas generales
    public static final String QUEUE_ALERTAS = "queues_alertasmedicas";
    public static final String EXCHANGE_ALERTAS = "exchanges_alertasmedicas";
    public static final String ROUTING_KEY = "alertas_routing_key";

    // Cola, Exchange y Routing Key para alertas graves
    public static final String QUEUE_ALERTAS_GRAVES = "queues_alertasgraves";
    public static final String EXCHANGE_ALERTAS_GRAVES = "exchanges_alertasgraves";
    public static final String ROUTING_KEY_GRAVES = "alertasgraves_routing_key";

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost, rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        return connectionFactory;
    }

    @PostConstruct
    public void logRabbitMQConfig() {
        System.out.println("🐇 RabbitMQ Config:");
        System.out.println("Host: " + rabbitmqHost);
        System.out.println("Port: " + rabbitmqPort);
        System.out.println("Username: " + rabbitmqUsername);
    }

    // Configuración común: Conversor Jackson para serialización/deserialización
    @Bean
    public Jackson2JsonMessageConverter jacksonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jacksonConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonConverter);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jacksonConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jacksonConverter);
        return factory;
    }
}