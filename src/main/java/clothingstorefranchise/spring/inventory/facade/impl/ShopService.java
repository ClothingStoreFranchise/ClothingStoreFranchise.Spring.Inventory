package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import clothingstorefranchise.spring.common.exceptions.EntityAlreadyExistsException;
import clothingstorefranchise.spring.common.exceptions.InvalidDataException;
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
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

@Service
public class ShopService extends BaseService<Shop, Long, IShopRepository> implements IShopService{
	
	@Autowired
	private IShopStockService shopStockService;
	
	@Autowired
	private IntegrationEventLogService integrationEventService;
	
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
	
	@Transactional
	public ShopDto create(ShopDto shopDto) {
		createValidationActions(shopDto);
		
		Shop shop = super.createBase(shopDto);
		CreateShopEvent event = map(shop, CreateShopEvent.class);
		integrationEventService.saveEvent(event);
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
	
	@Transactional
	public ShopDto update(ShopDto shopDto) {
		updateValidationActions(shopDto);
		
		Shop shop = super.updateBase(shopDto);
		UpdateShopEvent event = map(shop, UpdateShopEvent.class);
		integrationEventService.saveEvent(event);
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
	
	protected boolean entityAlreadyExistsCondition(ShopDto dto) {
		return repository.findById(dto.key()).isPresent();
	}

	protected boolean isValid(ShopDto dto) {
		return nullValidation(dto);
	}
	
	private void createValidationActions(ShopDto dto) {
		if(!isValid(dto)) 
			throw new InvalidDataException("Invalid data");
				
		if(dto.key() != null && entityAlreadyExistsCondition(dto))
			throw new EntityAlreadyExistsException("Entity already exists: "+dto.key());
	}
	
	private void updateValidationActions(ShopDto dto) {
		if(!isValid(dto)) 
			throw new InvalidDataException("Invalid data");
	}
	
	private static boolean nullValidation(ShopDto dto) {
		return dto != null
			&& !StringUtils.isAnyBlank(dto.getAddress())
			&& !StringUtils.isAnyBlank(dto.getPhone());
	}
}
