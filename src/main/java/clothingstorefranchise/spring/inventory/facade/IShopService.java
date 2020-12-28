package clothingstorefranchise.spring.inventory.facade;

import java.util.List;

import clothingstorefranchise.spring.inventory.dtos.ShopDto;
import clothingstorefranchise.spring.inventory.dtos.ShopWithStockDto;

public interface IShopService {
	ShopDto create(ShopDto shopDto);
	
	List<ShopDto> loadAll();
	
	ShopWithStockDto load(Long id);
	
	ShopDto update(ShopDto shopDto);
	
	void delete(Long id);
	
	List<ShopWithStockDto> findShopsWithStocksByProductId(Long productId);
		
	List<ShopDto> findShopsWithoutProductStock(Long productId);
	
	List<ShopWithStockDto> addProductToShops(Long productId, Long[] shopIds);
}
