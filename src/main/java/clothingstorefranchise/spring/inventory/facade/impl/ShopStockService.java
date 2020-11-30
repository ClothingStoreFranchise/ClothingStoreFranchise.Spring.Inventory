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
import clothingstorefranchise.spring.inventory.model.Product;
import clothingstorefranchise.spring.inventory.model.ShopStock;
import clothingstorefranchise.spring.inventory.model.ShopStockPK;
import clothingstorefranchise.spring.inventory.repositories.IProductRepository;
import clothingstorefranchise.spring.inventory.repositories.IShopStockRepository;
import clothingstorefranchise.spring.inventory.repositories.IWarehouseRepository;

@Service
public class ShopStockService extends BaseService<ShopStock, Long, IShopStockRepository>
	implements IShopStockService {
	
	private IShopStockRepository shopStockRepository;
	
	private IProductRepository productRepository;

	@Autowired
	public ShopStockService(IShopStockRepository shopStockRepository, IProductRepository productRepository) {
		super(ShopStock.class, shopStockRepository);
		this.shopStockRepository = shopStockRepository;
		this.productRepository = productRepository;
	}

	public List<StockDto> findByShopId(Long id) {
		List<ShopStock> shopStock = shopStockRepository.findByShopId(id);		
		return mapList(shopStock, StockDto.class);
	}
	
	public List<StockDto> addProductToShop(Long productId, Long shopId) {
		
		Product product = productRepository.findById(productId).get();
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
