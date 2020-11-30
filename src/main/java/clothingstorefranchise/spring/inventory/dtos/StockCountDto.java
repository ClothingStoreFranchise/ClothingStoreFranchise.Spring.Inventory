package clothingstorefranchise.spring.inventory.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockCountDto {
	
	@JsonProperty("productId")
	private Long idProduct;
    
	@JsonProperty("size")
	private int idSize;
	
	private Long stock;
	
	public StockCountDto(Long productId, int idSize, Long stock) {
		this.idProduct = productId;
		this.idSize = idSize;
		this.stock = stock;
	}
}
