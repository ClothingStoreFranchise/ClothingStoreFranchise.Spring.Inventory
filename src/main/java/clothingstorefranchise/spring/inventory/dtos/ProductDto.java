package clothingstorefranchise.spring.inventory.dtos;

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
public class ProductDto extends AbstractExtensibleEntityDto implements IEntityDto<Long> {
	private Long id;

	private double unitPrice;
	
	private String name;
	
	private String pictureUrl;
	
	private int clothingSizeType;

	@Override
	public Long key() {
		return id;
	}

	@Override
	public String getExtensibleEntityName() {
		return ProductDto.class.getSimpleName();
	}
}
