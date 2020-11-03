package clothingstorefranchise.spring.inventory.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockCountDto {
    	
	private int idSize;
	
	private Long stock;
	
	public StockCountDto(int idSize, Long stock) {
		this.idSize = idSize;
		this.stock = stock;
	}
}
