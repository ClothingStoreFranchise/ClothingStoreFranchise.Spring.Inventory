package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.dtos.ProductDto;
import clothingstorefranchise.spring.inventory.dtos.ProductInventoryDto;
import clothingstorefranchise.spring.inventory.dtos.ShopWithStockDto;
import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.dtos.WarehouseWithStockDto;
import clothingstorefranchise.spring.inventory.dtos.events.CreateProductEvent;
import clothingstorefranchise.spring.inventory.facade.IProductService;
import clothingstorefranchise.spring.inventory.facade.IShopService;
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
	IWarehouseStockService warehouseStockService;
	
	
	@Autowired
	public ProductService(IProductRepository productRepository) {
		super(Product.class, productRepository);
	}

	public void create(CreateProductEvent event) {
		Product product = map(event, Product.class);
		repository.save(product);
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
		List<StockCountDto> totalWarehouseStock = warehouseStockService.countTotalProductStocks(productId);
		
		return ProductInventoryDto.builder().shops(shopDtos).warehouses(warehouseDtos).totalWarehouseStock(totalWarehouseStock).build();
	}
	
	public ProductInventoryDto loadProductStockWithoutWarehouse(Long productId) {
		ProductInventoryDto product = map(this.loadBase(productId), ProductInventoryDto.class);
		List<ShopWithStockDto> shopDtos = shopService.findShopsWithStocksByProductId(productId);
		List<StockCountDto> totalWarehouseStock = warehouseStockService.countTotalProductStocks(productId);
		product.setShops(shopDtos);
		product.setTotalWarehouseStock(totalWarehouseStock);
		return product;
	}
	
}
