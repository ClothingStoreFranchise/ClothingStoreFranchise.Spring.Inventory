package clothingstorefranchise.spring.inventory.dtos.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import clothingstorefranchise.spring.common.event.IntegrationEvent;
import clothingstorefranchise.spring.common.types.IEntityDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductEvent extends IntegrationEvent implements IEntityDto<Long> {
	@JsonProperty("Id")
	private Long id;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("UnitPrice")
	private double unitPrice;
	
	@JsonProperty("PictureUrl")
	private String pictureUrl;
	
	@JsonProperty("ClothingSizeType")
	private int clothingSizeType;
	
	@JsonProperty("Ab")
	private int ab;
	
	@Override
	public Long key() {
		return id;
	}
}
