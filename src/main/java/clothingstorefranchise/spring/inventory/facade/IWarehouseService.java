package clothingstorefranchise.spring.inventory.facade;

import java.util.List;

import clothingstorefranchise.spring.inventory.dtos.WarehouseDto;
import clothingstorefranchise.spring.inventory.dtos.WarehouseWithStockDto;

public interface IWarehouseService {
	
	WarehouseDto create(WarehouseDto warehouseDto);
	
	List<WarehouseDto> loadAll();
	
	WarehouseDto load(Long id);
	
	WarehouseDto update(WarehouseDto warehouseDto);
	
	void delete(Long id);
	
	List<WarehouseWithStockDto> findWarehousesWithStocksByProductId(Long productId);
	
	List<WarehouseDto> findWarehouseWithoutProductStock(Long productId);
	
	List<WarehouseWithStockDto> addProductToWarehouses(Long productId, Long[] warehouseIds);
}
