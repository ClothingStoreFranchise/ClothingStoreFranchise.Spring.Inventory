package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.dtos.ProductDto;
import clothingstorefranchise.spring.inventory.dtos.ShopDto;
import clothingstorefranchise.spring.inventory.dtos.ShopWithStockDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.facade.IProductService;
import clothingstorefranchise.spring.inventory.facade.IShopService;
import clothingstorefranchise.spring.inventory.facade.IShopStockService;
import clothingstorefranchise.spring.inventory.model.Shop;
import clothingstorefranchise.spring.inventory.repositories.IShopRepository;

@Service
public class ShopService extends BaseService<Shop, Long, IShopRepository> implements IShopService{
	
	@Autowired
	private IShopStockService shopStockService;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	public ShopService(IShopRepository shopRepository) {
		super(Shop.class, shopRepository);
	}
	
	public List<ShopWithStockDto> findShopsWithStocksByProductId(Long productId){
		List<Shop> shops = repository.findShopsWithProductStocksByProductId(productId);
		return mapList(shops, ShopWithStockDto.class);
	}

	public ShopDto create(ShopDto shopDto) {
		Shop shop = super.createBase(shopDto);
		return map(shop, ShopDto.class);
	}

	public List<ShopDto> loadAll() {
		List<Shop> shops = super.loadAllBase();
		return mapList(shops, ShopDto.class);
	}

	public ShopDto load(Long id) {
		Shop shop = super.loadBase(id);
		return map(shop, ShopDto.class);
	}

	public ShopDto update(ShopDto shopDto) {
		Shop shop = super.updateBase(shopDto);
		return map(shop, ShopDto.class);
	}
	
	public List<ShopDto> findShopsWithoutProductStock(Long productId) {
		List<Shop> shops = repository.findShopsNotMatchProductId(productId);
		return mapList(shops, ShopDto.class);
	}
	
	public List<ShopWithStockDto> addProductToShops(Long productId, Long[] shopIds){
		
		List<ShopWithStockDto> shops = new ArrayList<>();
		ProductDto product = productService.load(productId);
		
		for(Long id : shopIds) {
			List<StockDto> stock = this.shopStockService.addProductToShop(product, id);
			ShopWithStockDto shop = map(this.load(id), ShopWithStockDto.class);
			shop.setShopStocks(stock);
			
			shops.add(shop);
		}
		
		return shops;
	}
}
