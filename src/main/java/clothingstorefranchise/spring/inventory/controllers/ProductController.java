package clothingstorefranchise.spring.inventory.controllers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import clothingstorefranchise.spring.inventory.dtos.CreateProductEvent;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/product")
@Api(value = "Endpoints to manage User´s information")
public class ProductController {
	
	@Autowired
    RabbitTemplate rabbitTemplate;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void sendEvent() {
		CreateProductEvent productCreatedEvent = 
				CreateProductEvent.builder().name("pantalon").id(1l).unitPrice(14.1).stock(7).pictureUrl("aaa").build();
		//String name = CreateProductEvent.class.getName();
		rabbitTemplate.convertAndSend("event_bus", "CreateProductEvent", productCreatedEvent);
	}
}
