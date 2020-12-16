package clothingstorefranchise.spring.inventory.dtos;

import clothingstorefranchise.spring.common.types.IEntityDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto implements IEntityDto<Long> {
	
	private Long id;
	
	private Long productId;
	
	private int size;

	private Long quantity;
	
	private int state;
	
	private Long warehouseId;

	@Override
	public Long key() {
		return productId;
	}
}
