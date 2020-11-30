package clothingstorefranchise.spring.inventory.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {	
	@Id
    @EqualsAndHashCode.Include
    @Column(insertable = true)
	private Long id;
	
	@NotNull
	@Column(nullable = false)
	private double unitPrice;
	
	@NotNull
	@Column(nullable = false)
	private String name;
	
	@NotNull
	@Column(nullable = false)
	private String pictureUrl;
	
	@NotNull
	@Column(nullable = false)
	private int clothingSizeType;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Collection<ShopStock> shopStock;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	private Collection<WarehouseStock> warehouseStock;
}
