package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.RabbitMqConfig;
import clothingstorefranchise.spring.inventory.dtos.ShopDto;
import clothingstorefranchise.spring.inventory.dtos.ShopWithStockDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.dtos.events.CreateShopEvent;
import clothingstorefranchise.spring.inventory.dtos.events.UpdateShopEvent;
import clothingstorefranchise.spring.inventory.facade.IShopService;
import clothingstorefranchise.spring.inventory.facade.IShopStockService;
import clothingstorefranchise.spring.inventory.model.Shop;
import clothingstorefranchise.spring.inventory.repositories.IShopRepository;

@Service
public class ShopService extends BaseService<Shop, Long, IShopRepository> implements IShopService{
	
	@Autowired
	private IShopStockService shopStockService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
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
		CreateShopEvent event = map(shop, CreateShopEvent.class);
		rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, CreateShopEvent.class.getSimpleName(), event);
		
		return map(shop, ShopDto.class);
	}

	public List<ShopDto> loadAll() {
		List<Shop> shops = super.loadAllBase();
		return mapList(shops, ShopDto.class);
	}

	public ShopWithStockDto load(Long id) {
		Shop warehouse = super.loadBase(id);
		return map(warehouse, ShopWithStockDto.class);
	}

	public ShopDto update(ShopDto shopDto) {
		Shop shop = super.updateBase(shopDto);
		UpdateShopEvent event = map(shop, UpdateShopEvent.class);
		rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, UpdateShopEvent.class.getSimpleName(), event);
		
		return map(shop, ShopDto.class);
	}
	
	public List<ShopDto> findShopsWithoutProductStock(Long productId) {
		List<Shop> shops = repository.findShopsNotMatchProductId(productId);
		return mapList(shops, ShopDto.class);
	}
	
	public List<ShopWithStockDto> addProductToShops(Long productId, Long[] shopIds){
		
		List<ShopWithStockDto> shops = new ArrayList<>();
		
		for(Long id : shopIds) {
			List<StockDto> stock = this.shopStockService.addProductToShop(productId, id);
			ShopWithStockDto shop = map(this.load(id), ShopWithStockDto.class);
			shop.setShopStocks(stock);
			
			shops.add(shop);
		}
		
		return shops;
	}
}
