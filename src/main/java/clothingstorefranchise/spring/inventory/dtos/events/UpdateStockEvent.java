package clothingstorefranchise.spring.inventory.dtos.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStockEvent {
	
	@JsonProperty("ProductId")
	private Long productId;
	
	@JsonProperty("Size")
	private int idSize;
	
	@JsonProperty("Stock")
	private Long stock;
}
