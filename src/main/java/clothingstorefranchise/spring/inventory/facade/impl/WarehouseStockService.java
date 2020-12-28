package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.RabbitMqConfig;
import clothingstorefranchise.spring.inventory.definitions.consts.ClothingSizes;
import clothingstorefranchise.spring.inventory.definitions.enums.OrderState;
import clothingstorefranchise.spring.inventory.dtos.OrderProductDto;
import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.dtos.events.UpdateStockEvent;
import clothingstorefranchise.spring.inventory.dtos.events.ValidateInventoryEvent;
import clothingstorefranchise.spring.inventory.facade.IWarehouseStockService;
import clothingstorefranchise.spring.inventory.model.BuildingStockPK;
import clothingstorefranchise.spring.inventory.model.Product;
import clothingstorefranchise.spring.inventory.model.WarehouseStock;
import clothingstorefranchise.spring.inventory.repositories.IProductRepository;
import clothingstorefranchise.spring.inventory.repositories.IWarehouseStockRepository;

@Service
public class WarehouseStockService extends BaseService<WarehouseStock, Long, IWarehouseStockRepository>
	implements  IWarehouseStockService {
	
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	public WarehouseStockService(IWarehouseStockRepository warehouseStockRepository){
		super(WarehouseStock.class, warehouseStockRepository);
	}
	
	public List<StockDto> addProductToWarehouse(Long productId,Long warehouseId) {
		
		Product product = productRepository.findById(productId).get();
		int[] sizes = ClothingSizes.getClothingShizes(product.getClothingSizeType());
		List<WarehouseStock> warehouseStocks = new ArrayList<>();
		
		for(int i = 0; i<sizes.length; i++) {
			BuildingStockPK pk = BuildingStockPK.builder().buildingId(warehouseId).productId(productId).size(sizes[i]).build();
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
	
	public ValidateInventoryEvent validateInventory(ValidateInventoryEvent event) { 
		List<OrderProductDto> orderProductDtos = event.getOrderProducts();
		List<StockCountDto> updatedStocks = new ArrayList<>();
		
		for(OrderProductDto orderProduct : orderProductDtos) {

			List<WarehouseStock> warehouseStocks = 
					repository.findByProductIdAndSizeWhereStockIsGreaterThanQuantityAndStockIsMax(orderProduct.getProductId(), orderProduct.getSize(), orderProduct.getQuantity());
			
			if(!warehouseStocks.isEmpty()) {
				WarehouseStock warehouseStock = warehouseStocks.iterator().next();
				Long stock = warehouseStock.getStock() - orderProduct.getQuantity();
				orderProduct.setWarehouseId(warehouseStock.getId().getBuildingId());
				orderProduct.setState(OrderState.CONFIRMED);
				
				warehouseStock.setStock(stock);
				repository.save(warehouseStock);
				
				StockCountDto stockCount = repository.countTotalProductStockByProductIdAndSize(warehouseStock.getId().getProductId(), warehouseStock.getId().getSize());
				updatedStocks.add(stockCount);
			} else {
				orderProduct.setState(OrderState.CANCELLED);
			}
		}
		
		//update customers microservice
		if(!updatedStocks.isEmpty()) {
			UpdateStockEvent updateStockEvent = new UpdateStockEvent(updatedStocks);
			rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, UpdateStockEvent.class.getSimpleName(), updateStockEvent);
		}
		
		event.setOrderProducts(orderProductDtos);
		
		return event;
 	}
}
