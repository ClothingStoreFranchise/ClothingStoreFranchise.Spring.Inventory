package clothingstorefranchise.spring.inventory.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopWithStockDto extends ShopDto {
	
	private List<StockDto> shopStocks;
}
