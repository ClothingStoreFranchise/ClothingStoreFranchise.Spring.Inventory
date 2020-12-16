package clothingstorefranchise.spring.inventory.facade;

import java.util.List;

import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.dtos.events.ValidateInventoryEvent;

public interface IWarehouseStockService {
	List<StockDto> findByWarehouseId(Long id);
	
	List<StockDto> addProductToWarehouse(Long productId,Long warehouseId);
	
	List<StockCountDto> countTotalProductStocks(List<Long> productIds);
	
	ValidateInventoryEvent validateInventory(ValidateInventoryEvent event);
	
	void deleteByProductId(Long productId);
}
