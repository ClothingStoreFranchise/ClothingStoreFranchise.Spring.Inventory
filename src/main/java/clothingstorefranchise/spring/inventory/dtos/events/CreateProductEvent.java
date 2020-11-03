package clothingstorefranchise.spring.inventory.dtos.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import clothingstorefranchise.spring.common.event.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductEvent extends IntegrationEvent {
	
	@JsonProperty("Id")
	private Long id;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("UnitPrice")
	private double unitPrice;
	
	@JsonProperty("PictureUrl")
	private String pictureUrl;
	
	@JsonProperty("TypeClothingSize")
	private int typeClothingSize;
}
