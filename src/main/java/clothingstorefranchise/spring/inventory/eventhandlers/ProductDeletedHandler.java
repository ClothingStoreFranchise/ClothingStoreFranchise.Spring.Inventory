package clothingstorefranchise.spring.inventory.eventhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.common.event.IIntegrationEventHandler;
import clothingstorefranchise.spring.inventory.RabbitMqConfig;
import clothingstorefranchise.spring.inventory.dtos.events.DeleteProductEvent;
import clothingstorefranchise.spring.inventory.facade.IProductService;

@Service
public class ProductDeletedHandler implements IIntegrationEventHandler<DeleteProductEvent> {
	private final Logger logger = LoggerFactory.getLogger(ProductDeletedHandler.class);
	
	@Autowired
	private IProductService productService;

	@Override
	@Async
    @EventListener
	public void handle(DeleteProductEvent event) {
		logger.info("DeleteProductEvent received");	
		productService.deleteProduct(event.key());
	}
}
