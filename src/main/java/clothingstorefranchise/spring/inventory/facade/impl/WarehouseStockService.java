package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.exception.DataException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.RabbitMqConfig;
import clothingstorefranchise.spring.inventory.definitions.consts.ClothingSizes;
import clothingstorefranchise.spring.inventory.dtos.ProductDto;
import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.dtos.events.UpdateStockEvent;
import clothingstorefranchise.spring.inventory.facade.IWarehouseStockService;
import clothingstorefranchise.spring.inventory.model.Product;
import clothingstorefranchise.spring.inventory.model.WarehouseStock;
import clothingstorefranchise.spring.inventory.model.WarehouseStockPK;
import clothingstorefranchise.spring.inventory.repositories.IProductRepository;
import clothingstorefranchise.spring.inventory.repositories.IWarehouseStockRepository;

@Service
public class WarehouseStockService extends BaseService<WarehouseStock, Long, IWarehouseStockRepository>
	implements  IWarehouseStockService {
	
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	public WarehouseStockService(IWarehouseStockRepository warehouseStockRepository){
		super(WarehouseStock.class, warehouseStockRepository);
	}
	
	public List<StockDto> findByWarehouseId(Long id) {
		List<WarehouseStock> entities = repository.findByWarehouseIdOrderByIdSizeAscQuery(id);
		return mapList(entities, StockDto.class);
	}
	
	public List<StockDto> addProductToWarehouse(Long productId,Long warehouseId) {
		
		Product product = productRepository.findById(productId).get();
		int[] sizes = ClothingSizes.getClothingShizes(product.getClothingSizeType());
		List<WarehouseStock> warehouseStocks = new ArrayList<>();
		
		for(int i = 0; i<sizes.length; i++) {
			WarehouseStockPK pk = WarehouseStockPK.builder().warehouseId(warehouseId).productId(productId).size(sizes[i]).build();
			warehouseStocks.add(WarehouseStock.builder().id(pk).stock(12l).build());
		}
		
		List<WarehouseStock> warehouseStocksCreated = createAction(warehouseStocks);		
		
		return mapList(warehouseStocksCreated, StockDto.class);
	}
	
	public List<StockCountDto> countTotalProductStocks(List<Long> productIds){
		
		return repository.countTotalProductStocks(productIds);
	}
	
	public void deleteByProductId(Long productId) {
		
		repository.deleteStockByProductId(productId);
	}
}
