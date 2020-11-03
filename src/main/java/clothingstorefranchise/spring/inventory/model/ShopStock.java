package clothingstorefranchise.spring.inventory.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumns;
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
public class ShopStock {
	
	@EmbeddedId
	private ShopStockPK id;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Product.class)
	@MapsId("product_id")
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Shop.class)
	@MapsId("shop_id")
    @JoinColumn(name = "shop_id", nullable = false, insertable = false, updatable = false)
	private Shop shop;
	
	@Column(nullable = false)
	private Long stock;
}
