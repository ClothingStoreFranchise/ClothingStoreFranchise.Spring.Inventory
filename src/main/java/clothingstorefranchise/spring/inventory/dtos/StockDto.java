package clothingstorefranchise.spring.inventory.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import clothingstorefranchise.spring.common.extensible.AbstractExtensibleEntityDto;
import clothingstorefranchise.spring.common.types.IEntityDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDto extends AbstractExtensibleEntityDto implements IEntityDto<Long> {
	
	private Long shopId;
    
    private Long warehouseId;
    
    private ProductDto product;
    
    @JsonProperty("size")
	private int idSize;
	
	private Long stock;

	@Override
	public Long key() {
		return product.getId();
	}

	@Override
	public String getExtensibleEntityName() {
		return null;
	}
}
