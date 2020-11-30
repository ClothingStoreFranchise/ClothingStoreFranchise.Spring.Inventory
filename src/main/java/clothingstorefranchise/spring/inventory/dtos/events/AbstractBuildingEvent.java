package clothingstorefranchise.spring.inventory.dtos.events;

import clothingstorefranchise.spring.common.event.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractBuildingEvent extends IntegrationEvent {

	private Long id;
	
	private String address;
	
	private String phone;
}
