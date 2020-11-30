package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.dtos.ProductDto;
import clothingstorefranchise.spring.inventory.dtos.ProductInventoryDto;
import clothingstorefranchise.spring.inventory.dtos.ShopWithStockDto;
import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import clothingstorefranchise.spring.inventory.dtos.WarehouseWithStockDto;
import clothingstorefranchise.spring.inventory.dtos.events.CreateProductEvent;
import clothingstorefranchise.spring.inventory.dtos.events.UpdateProductEvent;
import clothingstorefranchise.spring.inventory.facade.IProductService;
import clothingstorefranchise.spring.inventory.facade.IShopService;
import clothingstorefranchise.spring.inventory.facade.IShopStockService;
import clothingstorefranchise.spring.inventory.facade.IWarehouseService;
import clothingstorefranchise.spring.inventory.facade.IWarehouseStockService;
import clothingstorefranchise.spring.inventory.model.Product;
import clothingstorefranchise.spring.inventory.repositories.IProductRepository;

@Service
public class ProductService extends BaseService<Product, Long, IProductRepository>
	implements IProductService {
	
	@Autowired
	private IShopService shopService;
	
	@Autowired
	private IWarehouseService warehouseService;
	
	@Autowired
	private IWarehouseStockService warehouseStockService;
	
	@Autowired
	private IShopStockService shopStockService;
	
	@Autowired
	public ProductService(IProductRepository productRepository) {
		super(Product.class, productRepository);
	}

	public void create(CreateProductEvent event) {
		super.createBase(event);
	}
	
	public void update(UpdateProductEvent event) {
		super.updateBase(event);
	}
	
	public List<ProductDto> loadAll() {
		List<Product> products = super.loadAllBase();
		return mapList(products, ProductDto.class);
	}
	
	public ProductDto load(Long id) {
		Product product = super.loadBase(id);
		return map(product, ProductDto.class);
	}
	
	public ProductInventoryDto loadProductStock(Long productId) {
		List<ShopWithStockDto> shopDtos = shopService.findShopsWithStocksByProductId(productId);
		List<WarehouseWithStockDto> warehouseDtos = warehouseService.findWarehousesWithStocksByProductId(productId);
		List<StockCountDto> totalWarehouseStock = warehouseStockService.countTotalProductStocks(Arrays.asList(productId));
		
		return ProductInventoryDto.builder().shops(shopDtos).warehouses(warehouseDtos).totalWarehouseStock(totalWarehouseStock).build();
	}
	
	public ProductInventoryDto loadProductStockWithoutWarehouse(Long productId) {
		ProductInventoryDto product = map(this.loadBase(productId), ProductInventoryDto.class);
		List<ShopWithStockDto> shopDtos = shopService.findShopsWithStocksByProductId(productId);
		List<StockCountDto> totalWarehouseStock = warehouseStockService.countTotalProductStocks(Arrays.asList(productId));
		product.setShops(shopDtos);
		product.setTotalWarehouseStock(totalWarehouseStock);
		return product;
	}
	
	public void deleteProduct(Long id) {
		//be careful with composite key 
		this.warehouseStockService.deleteByProductId(id);
		this.shopStockService.deleteByProductId(id);
		super.delete(id);
	}
}
