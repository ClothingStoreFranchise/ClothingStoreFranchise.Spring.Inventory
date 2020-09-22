package clothingstorefranchise.spring.inventory.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ShopStockPK implements Serializable{
		
	@Column(name = "size_id")
    protected Long sizeId;
	
	@Column(name = "product_id")
    protected Long productId;
	
	@Column(name = "shop_id")
    protected Long shopId;
}
