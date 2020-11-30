package clothingstorefranchise.spring.inventory.dtos;

import java.util.Collection;

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
public class ShopDto extends AbstractExtensibleEntityDto implements IEntityDto<Long> {
	private Long id;
	
	private String address;
	
	private String phone;
		
	@Override
	public Long key() {
		return id;
	}

	@Override
	public String getExtensibleEntityName() {
		return ShopDto.class.getSimpleName();
	}  
}
