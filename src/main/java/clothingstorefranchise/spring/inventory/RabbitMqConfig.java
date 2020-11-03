package clothingstorefranchise.spring.inventory;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import clothingstorefranchise.spring.inventory.dtos.events.CreateProductEvent;

@Configuration
public class RabbitMqConfig {
	
	public static final String DEAD_LETTER_EXCHANGE = "dead_letter";
	
    public static final String EXCHANGE_NAME = "event_bus";
    
	public static final String QUEUE = "inventory";

    private static final String PARKINGLOT_QUEUE = QUEUE + ".parkingLot";

    
    @Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange(DEAD_LETTER_EXCHANGE);
	}
	
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue primaryQueue() {
        return QueueBuilder.durable(QUEUE)
            .deadLetterExchange(DEAD_LETTER_EXCHANGE)
            .deadLetterRoutingKey(PARKINGLOT_QUEUE)
            .build();
    }

    @Bean
    Queue parkinglotQueue() {
        return new Queue(PARKINGLOT_QUEUE);
    }

    @Bean
    Binding primaryBinding() {
        return BindingBuilder.bind(primaryQueue()).to(exchange()).with(CreateProductEvent.class.getSimpleName());
    }
    
    /*@Bean
    Binding secondaryBinding() {
    	return BindingBuilder.bind(primaryQueue()).to(exchange()).with("abab");
    }*/

    @Bean
    Binding parkingBinding() {
        return BindingBuilder.bind(parkinglotQueue()).to(deadLetterExchange()).with(PARKINGLOT_QUEUE);
    }
    
    @Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
