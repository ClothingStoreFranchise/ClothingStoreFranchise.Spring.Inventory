package clothingstorefranchise.spring.inventory.dtos.events;

import java.util.List;

import clothingstorefranchise.spring.inventory.dtos.OrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateInventoryEvent {
	
	private List<OrderProductDto> orderProducts;	
}
