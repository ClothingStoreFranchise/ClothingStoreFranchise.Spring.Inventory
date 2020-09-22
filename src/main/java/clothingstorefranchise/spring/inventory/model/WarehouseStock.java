package clothingstorefranchise.spring.inventory.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

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
public class WarehouseStock {
	
	@EmbeddedId
	private WarehouseStockPK id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = ProductSize.class)
    @JoinColumn(name = "product_id", nullable = false,insertable = false, updatable = false)
	@JoinColumn(name = "size_id", nullable = false, insertable = false, updatable = false)
	private ProductSize productSize;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Warehouse.class)
	@MapsId("warehouse_id")
    @JoinColumn(name = "warehouse_id", nullable = false, insertable = false, updatable = false)
	private Warehouse warehouse;
	
	@Column(nullable = false)
	private int stock;
}
