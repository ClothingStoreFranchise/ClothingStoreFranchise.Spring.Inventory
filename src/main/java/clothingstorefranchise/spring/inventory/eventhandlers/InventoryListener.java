package clothingstorefranchise.spring.inventory.eventhandlers;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import clothingstorefranchise.spring.common.event.EventBusSubscriptions;
import clothingstorefranchise.spring.inventory.RabbitMqConfig;

@Service
public class InventoryListener implements MessageListener {
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
    private EventBusSubscriptions subscriptions;

	@RabbitListener(queues = RabbitMqConfig.QUEUE)
	public void onMessage(Message message) {
		
		String eventName = message.getMessageProperties().getReceivedRoutingKey();
		Class<?> eventType = subscriptions.getEventType(eventName);
		String event = new String(message.getBody(), StandardCharsets.UTF_8);
		try {
			eventPublisher.publishEvent(new ObjectMapper().readValue(event, eventType));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
	}
}
