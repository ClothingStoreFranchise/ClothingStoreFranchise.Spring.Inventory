package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.RabbitMqConfig;
import clothingstorefranchise.spring.inventory.dtos.ProductInventoryDto;
import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.dtos.WarehouseDto;
import clothingstorefranchise.spring.inventory.dtos.WarehouseWithStockDto;
import clothingstorefranchise.spring.inventory.dtos.events.CreateWarehouseEvent;
import clothingstorefranchise.spring.inventory.dtos.events.UpdateStockEvent;
import clothingstorefranchise.spring.inventory.dtos.events.UpdateWarehouseEvent;
import clothingstorefranchise.spring.inventory.facade.IWarehouseService;
import clothingstorefranchise.spring.inventory.facade.IWarehouseStockService;
import clothingstorefranchise.spring.inventory.model.Warehouse;
import clothingstorefranchise.spring.inventory.repositories.IWarehouseRepository;

@Service
public class WarehouseService extends BaseService<Warehouse, Long, IWarehouseRepository> 
	implements IWarehouseService {
	
	@Autowired
	IWarehouseStockService warehouseStockService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
		
	@Autowired
	public WarehouseService(IWarehouseRepository warehouseRepository) {
		super(Warehouse.class, warehouseRepository);
	}
	
	public List<WarehouseWithStockDto> findWarehousesWithStocksByProductId(Long productId) {
		List<Warehouse> warehouses = repository.findWarehousesWithProductStocksByProductId(productId);
		return mapList(warehouses, WarehouseWithStockDto.class);
	}

	public WarehouseDto create(WarehouseDto warehouseDto) {
		Warehouse warehouse = super.createBase(warehouseDto);
		CreateWarehouseEvent event = map(warehouse, CreateWarehouseEvent.class);
		rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, CreateWarehouseEvent.class.getSimpleName(), event);
		
		return map(warehouse, WarehouseDto.class);
	}

	public List<WarehouseDto> loadAll() {
		List<Warehouse> warehouses = super.loadAllBase();
		return mapList(warehouses, WarehouseDto.class);
	}

	public WarehouseWithStockDto load(Long id) {
		Warehouse warehouse = super.loadBase(id);
		return map(warehouse, WarehouseWithStockDto.class);
	}

	public WarehouseDto update(WarehouseDto warehouseDto) {
		Warehouse warehouse = super.updateBase(warehouseDto);
		UpdateWarehouseEvent event = map(warehouse, UpdateWarehouseEvent.class);
		
		rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, UpdateWarehouseEvent.class.getSimpleName(), event);
		
		return map(warehouse, WarehouseDto.class);
	}
	
	public List<WarehouseDto> findWarehouseWithoutProductStock(Long productId){
		List<Warehouse> warehouse = repository.findWarehousesNotMatchProductId(productId);
		return mapList(warehouse, WarehouseDto.class);
	}
	
	 public ProductInventoryDto addProductToWarehouses(Long productId, Long[] warehouseIds){
		 
		 List<WarehouseWithStockDto> warehouses = new ArrayList<>();
		 
		 for(Long id : warehouseIds) {
			 List<StockDto> stock = this.warehouseStockService.addProductToWarehouse(productId, id);
			 WarehouseDto warehouseDto = this.load(id);
			 WarehouseWithStockDto warehouse = map(warehouseDto, WarehouseWithStockDto.class);
			 warehouse.setWarehouseStocks(stock);
			 
			 warehouses.add(warehouse);
		 }
		 
		//update data in other micorservices
		List<StockCountDto> totalWarehouseStock = warehouseStockService.countTotalProductStocks(Arrays.asList(productId));
		UpdateStockEvent event = new UpdateStockEvent(totalWarehouseStock);
		rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, UpdateStockEvent.class.getSimpleName(), event);
		
		ProductInventoryDto productInventory = ProductInventoryDto.builder().warehouses(warehouses).totalWarehouseStock(totalWarehouseStock).build();
		return productInventory;
	 }
	 
	void deleteByProductId(Long productId) {
		this.warehouseStockService.deleteByProductId(productId);
	}
}
