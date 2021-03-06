package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.common.exceptions.EntityDoesNotExistException;
import clothingstorefranchise.spring.inventory.definitions.consts.ClothingSizes;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.facade.IShopStockService;
import clothingstorefranchise.spring.inventory.model.BuildingStockPK;
import clothingstorefranchise.spring.inventory.model.Product;
import clothingstorefranchise.spring.inventory.model.ShopStock;
import clothingstorefranchise.spring.inventory.repositories.IProductRepository;
import clothingstorefranchise.spring.inventory.repositories.IShopStockRepository;

@Service
public class ShopStockService extends BaseService<ShopStock, Long, IShopStockRepository>
	implements IShopStockService {
	
	private IProductRepository productRepository;

	@Autowired
	public ShopStockService(IShopStockRepository shopStockRepository, IProductRepository productRepository) {
		super(ShopStock.class, shopStockRepository);
		this.productRepository = productRepository;
	}
	
	public List<StockDto> addProductToShop(Long productId, Long shopId) {
		
		Product product = productRepository.findById(productId).orElseThrow(() -> new EntityDoesNotExistException("Product not found: "+productId));
		int[] sizes = ClothingSizes.getClothingShizes(product.getClothingSizeType());
		List<ShopStock> shopStocks = new ArrayList<>();
		
		for(int i = 0; i<sizes.length; i++) {
			BuildingStockPK pk = BuildingStockPK.builder().buildingId(shopId).productId(product.getId()).size(sizes[i]).build();
			shopStocks.add(ShopStock.builder().id(pk).stock(12l).build());
		}
		
		List<ShopStock> shopStocksCreated = createAction(shopStocks);
		return mapList(shopStocksCreated, StockDto.class);
	}
	
	public void deleteByProductId(Long productId) {
		repository.deleteStockByProductId(productId);
	}
	
}
