package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.definitions.consts.ClothingSizes;
import clothingstorefranchise.spring.inventory.dtos.ProductDto;
import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.facade.IWarehouseStockService;
import clothingstorefranchise.spring.inventory.model.WarehouseStock;
import clothingstorefranchise.spring.inventory.model.WarehouseStockPK;
import clothingstorefranchise.spring.inventory.repositories.IWarehouseStockRepository;

@Service
public class WarehouseStockService extends BaseService<WarehouseStock, Long, IWarehouseStockRepository>
	implements  IWarehouseStockService {
	
	@Autowired
	public WarehouseStockService(IWarehouseStockRepository warehouseStockRepository){
		super(WarehouseStock.class, warehouseStockRepository);
	}
	
	public List<StockDto> findByWarehouseId(Long id) {
		List<WarehouseStock> entities = repository.findByWarehouseIdOrderByIdSizeAscQuery(id);
		return mapList(entities, StockDto.class);
	}
	
	public List<StockDto> addProductToWarehouse(ProductDto product,Long warehouseId) {
		
		int[] sizes = ClothingSizes.getClothingShizes(product.getClothingSizeType());
		List<WarehouseStock> warehouseStocks = new ArrayList<>();
		
		for(int i = 0; i<sizes.length; i++) {
			WarehouseStockPK pk = WarehouseStockPK.builder().warehouseId(warehouseId).productId(product.getId()).size(sizes[i]).build();
			warehouseStocks.add(WarehouseStock.builder().id(pk).stock(12l).build());
		}
		
		List<WarehouseStock> warehouseStocksCreated = createAction(warehouseStocks);
		return mapList(warehouseStocksCreated, StockDto.class);
	}
	
	public List<StockCountDto> countTotalProductStocks(Long productId){
		
		return repository.countTotalProductStocks(productId);
	}
}
