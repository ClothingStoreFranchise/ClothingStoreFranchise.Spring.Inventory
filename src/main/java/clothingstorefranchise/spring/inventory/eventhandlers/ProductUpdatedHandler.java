package clothingstorefranchise.spring.inventory.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import clothingstorefranchise.spring.common.event.IIntegrationEventHandler;
import clothingstorefranchise.spring.inventory.RabbitMqConfig;
import clothingstorefranchise.spring.inventory.dtos.events.UpdateProductEvent;
import clothingstorefranchise.spring.inventory.facade.IProductService;

public class ProductUpdatedHandler implements IIntegrationEventHandler<UpdateProductEvent> {
	private final Logger logger = LoggerFactory.getLogger(ProductUpdatedHandler.class);

	@Autowired
	private IProductService productService;
	
	@Override
	@RabbitListener(queues = RabbitMqConfig.QUEUE)
	public void handle(UpdateProductEvent event) {	
		logger.info("UpdateProductEvent received");
		
		productService.update(event);
	}
}
