package clothingstorefranchise.spring.inventory.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import clothingstorefranchise.spring.common.event.IIntegrationEventHandler;
import clothingstorefranchise.spring.inventory.RabbitMqConfig;
import clothingstorefranchise.spring.inventory.dtos.events.CreateProductEvent;
import clothingstorefranchise.spring.inventory.facade.IProductService;

@Component
public class ProductCreatedHandler implements IIntegrationEventHandler<CreateProductEvent> {
	
	private final Logger logger = LoggerFactory.getLogger(ProductCreatedHandler.class);
	
	@Autowired
	private IProductService productService;
	
	@Override
	@RabbitListener(queues = RabbitMqConfig.QUEUE )
    public void handle(CreateProductEvent event) {
		logger.info("CreateProductEvent received");
		productService.create(event);
	}
}
