package clothingstorefranchise.spring.inventory.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockCountDto {
    
	@JsonProperty("Size")
	private int idSize;
	
	@JsonProperty("Stock")
	private Long stock;
	
	public StockCountDto(int idSize, Long stock) {
		this.idSize = idSize;
		this.stock = stock;
	}
}
