package clothingstorefranchise.spring.inventory.dtos.events;

import java.util.List;

import clothingstorefranchise.spring.common.event.IntegrationEvent;
import clothingstorefranchise.spring.inventory.dtos.OrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidateInventoryEvent extends IntegrationEvent {
	
	private List<OrderProductDto> orderProducts;
}
