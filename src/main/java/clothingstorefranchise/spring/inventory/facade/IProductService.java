package clothingstorefranchise.spring.inventory.facade;

import java.util.List;

import clothingstorefranchise.spring.inventory.dtos.ProductDto;
import clothingstorefranchise.spring.inventory.dtos.ProductInventoryDto;
import clothingstorefranchise.spring.inventory.dtos.events.CreateProductEvent;
import clothingstorefranchise.spring.inventory.dtos.events.UpdateProductEvent;

public interface IProductService {
	
	void create(CreateProductEvent productEvent);
	
	void update(UpdateProductEvent event);
	
	List<ProductDto> loadAll();
	
	ProductDto load(Long id);
	
	ProductInventoryDto loadProductStock(Long productId);
	
	ProductInventoryDto loadProductStockWithoutWarehouse(Long productId);
	
	void deleteProduct(Long id);
}
