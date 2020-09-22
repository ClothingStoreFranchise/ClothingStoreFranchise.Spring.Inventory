package clothingstorefranchise.spring.inventory.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductSizePK implements Serializable{
	
	@Column()
    protected Long sizeId;
	
	@Column(name = "product_id")
    protected Long productId;
}
