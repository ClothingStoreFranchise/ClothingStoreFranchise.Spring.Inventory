package clothingstorefranchise.spring.inventory.dtos;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import clothingstorefranchise.spring.common.extensible.AbstractExtensibleEntityDto;
import clothingstorefranchise.spring.common.types.IEntityDto;
import clothingstorefranchise.spring.inventory.model.Employee;
import clothingstorefranchise.spring.inventory.model.WarehouseStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDto extends AbstractExtensibleEntityDto implements IEntityDto<Long>{
	
	private Long id;
	
	private String address;
	
	private String phone;
		
	//private Collection<Employee> employees;
	
	@Override
	public Long key() {
		return id;
	}

	@Override
	public String getExtensibleEntityName() {
		return WarehouseDto.class.getSimpleName();
	}

}
