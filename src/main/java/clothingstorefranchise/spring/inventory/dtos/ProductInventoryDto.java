package clothingstorefranchise.spring.inventory.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInventoryDto extends ProductDto{
	
	private List<ShopWithStockDto> shops;
				
	private List<WarehouseWithStockDto> warehouses;
	
	private List<StockCountDto> totalWarehouseStock;
}
