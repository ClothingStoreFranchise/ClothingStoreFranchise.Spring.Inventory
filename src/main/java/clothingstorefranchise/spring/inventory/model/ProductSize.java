package clothingstorefranchise.spring.inventory.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

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
public class ProductSize {
	
	@EmbeddedId
	ProductSizePK id;
	
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Product.class)
	@MapsId("product_id")
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
	private Product product;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productSize")
	private Collection<ShopStock> stockShops;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productSize")
	private Collection<WarehouseStock> stockWarehouses;
	
	@Column(nullable = false)
	private int stock;
	
	@Column(nullable = false)
	private int totalShopStock;
	
	@Column(nullable = false)
	private int totalWarehouseStock;
}
