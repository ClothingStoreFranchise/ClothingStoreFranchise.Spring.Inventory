package clothingstorefranchise.spring.inventory.dtos.events;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import clothingstorefranchise.spring.common.event.IntegrationEvent;
import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStockEvent extends IntegrationEvent {
	
	@JsonProperty("Stocks")
	private List<StockCountDto> totalWarehouseStock;
	
	public UpdateStockEvent(List<StockCountDto> totalWarehouseStock) {
		this.totalWarehouseStock = totalWarehouseStock;
	}
}
