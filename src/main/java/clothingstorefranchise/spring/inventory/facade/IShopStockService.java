package clothingstorefranchise.spring.inventory.facade;

import java.util.List;

import clothingstorefranchise.spring.inventory.dtos.StockDto;

public interface IShopStockService {
	List<StockDto> findByShopId(Long id);
	
	List<StockDto> addProductToShop(Long productId, Long shopId);
	
	void deleteByProductId(Long productId);
}
