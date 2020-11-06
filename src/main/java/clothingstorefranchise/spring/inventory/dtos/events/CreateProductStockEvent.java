package clothingstorefranchise.spring.inventory.dtos.events;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductStockEvent extends CreateProductEvent {
	private List<StockCountDto> totalWarehouseStock;
}
