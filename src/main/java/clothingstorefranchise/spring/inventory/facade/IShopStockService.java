package clothingstorefranchise.spring.inventory.facade;

import java.util.List;

import clothingstorefranchise.spring.inventory.dtos.ProductDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;

public interface IShopStockService {
	List<StockDto> findByShopId(Long id);
	
	List<StockDto> addProductToShop(ProductDto product, Long shopId);
	
	void deleteByProductId(Long productId);
}
