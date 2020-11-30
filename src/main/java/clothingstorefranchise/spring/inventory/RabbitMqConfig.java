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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import clothingstorefranchise.spring.common.event.EventBusSubscriptions;
import clothingstorefranchise.spring.inventory.dtos.events.CreateProductEvent;
import clothingstorefranchise.spring.inventory.dtos.events.DeleteProductEvent;
import clothingstorefranchise.spring.inventory.dtos.events.UpdateProductEvent;

@Configuration
@ComponentScan(value = "clothingstorefranchise.spring.common.event")
public class RabbitMqConfig {
	
	public static final String DEAD_LETTER_EXCHANGE = "dead_letter";
	
    public static final String EXCHANGE_NAME = "event_bus";
    
	public static final String QUEUE = "inventory";

    private static final String PARKINGLOT_QUEUE = QUEUE + ".parkingLot";
    
    @Autowired
    private EventBusSubscriptions subscriptions;
    
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
    Binding createProductEventBinding() {
    	subscriptions.addSubscription(CreateProductEvent.class);
        return BindingBuilder.bind(primaryQueue()).to(exchange()).with(CreateProductEvent.class.getSimpleName());
    }
    
    @Bean
    Binding deleteProductEventBinding() {
    	subscriptions.addSubscription(DeleteProductEvent.class);
        return BindingBuilder.bind(primaryQueue()).to(exchange()).with(DeleteProductEvent.class.getSimpleName());
    }
    
    @Bean
    Binding updateProductEventBinding() {
    	subscriptions.addSubscription(UpdateProductEvent.class);
        return BindingBuilder.bind(primaryQueue()).to(exchange()).with(UpdateProductEvent.class.getSimpleName());
    }

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
