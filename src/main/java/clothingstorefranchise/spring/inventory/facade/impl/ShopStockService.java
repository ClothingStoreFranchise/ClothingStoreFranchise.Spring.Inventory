package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.inventory.definitions.consts.ClothingSizes;
import clothingstorefranchise.spring.inventory.dtos.ProductDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.facade.IShopStockService;
import clothingstorefranchise.spring.inventory.model.ShopStock;
import clothingstorefranchise.spring.inventory.model.ShopStockPK;
import clothingstorefranchise.spring.inventory.repositories.IShopStockRepository;

@Service
public class ShopStockService extends BaseService<ShopStock, Long, IShopStockRepository>
	implements IShopStockService {
	
	private IShopStockRepository shopStockRepository;
	
	/*private PropertyMap<ShopStock, StockDto> propertyMap = new PropertyMap<ShopStock, StockDto>() {
		  protected void configure() {
			    map().setProductId(source.getProduct().getId());
			    map().setShopId(source.getShop().getId());
			    map().setSize(source.getId().getSize());
			  }
			};
		*/	
	@Autowired
	public ShopStockService(IShopStockRepository shopStockRepository) {
		super(ShopStock.class, shopStockRepository);
		this.shopStockRepository = shopStockRepository;		
		//modelMapper.addMappings(propertyMap);

//        this.modelMapper.addMappings(propertyMap);
	}

	public List<StockDto> findByShopId(Long id) {
		List<ShopStock> shopStock = shopStockRepository.findByShopId(id);		
		return mapList(shopStock, StockDto.class);
	}
	
	public List<StockDto> addProductToShop(ProductDto product, Long shopId) {
		
		int[] sizes = ClothingSizes.getClothingShizes(product.getClothingSizeType());
		List<ShopStock> shopStocks = new ArrayList<>();
		
		for(int i = 0; i<sizes.length; i++) {
			ShopStockPK pk = ShopStockPK.builder().shopId(shopId).productId(product.getId()).size(sizes[i]).build();
			shopStocks.add(ShopStock.builder().id(pk).stock(12l).build());
		}
		
		List<ShopStock> shopStocksCreated = createAction(shopStocks);
		return mapList(shopStocksCreated, StockDto.class);
	}
	
	public void deleteByProductId(Long productId) {
		repository.deleteStockByProductId(productId);
	}
	
}
