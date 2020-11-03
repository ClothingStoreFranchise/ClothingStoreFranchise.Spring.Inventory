package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.dtos.ProductDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.dtos.WarehouseDto;
import clothingstorefranchise.spring.inventory.dtos.WarehouseWithStockDto;
import clothingstorefranchise.spring.inventory.facade.IProductService;
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
	private IProductService productService;
		
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
		return map(warehouse, WarehouseDto.class);
	}

	public List<WarehouseDto> loadAll() {
		List<Warehouse> warehouses = super.loadAllBase();
		return mapList(warehouses, WarehouseDto.class);
	}

	public WarehouseDto load(Long id) {
		Warehouse warehouse = super.loadBase(id);
		return map(warehouse, WarehouseDto.class);
	}

	public WarehouseDto update(WarehouseDto warehouseDto) {
		Warehouse warehouse = super.updateBase(warehouseDto);
		return map(warehouse, WarehouseDto.class);
	}
	
	public List<WarehouseDto> findWarehouseWithoutProductStock(Long productId){
		List<Warehouse> warehouse = repository.findWarehousesNotMatchProductId(productId);
		return mapList(warehouse, WarehouseDto.class);
	}
	
	 public List<WarehouseWithStockDto> addProductToWarehouses(Long productId, Long[] warehouseIds){
		 
		 List<WarehouseWithStockDto> warehouses = new ArrayList<>();
		 ProductDto product = productService.load(productId);
		 
		 for(Long id : warehouseIds) {
			 List<StockDto> stock = this.warehouseStockService.addProductToWarehouse(product, id);
			 WarehouseWithStockDto warehouse = map(this.load(id), WarehouseWithStockDto.class);
			 warehouse.setWarehouseStocks(stock);
			 
			 warehouses.add(warehouse);
		 }
		 
		 return warehouses;
	 }
}
