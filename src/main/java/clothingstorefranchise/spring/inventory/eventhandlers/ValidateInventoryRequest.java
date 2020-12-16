package clothingstorefranchise.spring.inventory.eventhandlers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.RabbitMqConfig;
import clothingstorefranchise.spring.inventory.dtos.events.ValidateInventoryEvent;
import clothingstorefranchise.spring.inventory.facade.IWarehouseStockService;

@Service
public class ValidateInventoryRequest {
	
	@Autowired
	private IWarehouseStockService warehouseStockService;
	
	@RabbitListener(queues = RabbitMqConfig.QUEUE_REQUEST, concurrency = "10")
	public ValidateInventoryEvent validateInventory(ValidateInventoryEvent event) {
        return warehouseStockService.validateInventory(event);
	}
}
