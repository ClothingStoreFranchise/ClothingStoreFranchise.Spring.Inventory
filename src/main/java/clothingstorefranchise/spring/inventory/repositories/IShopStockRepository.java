package clothingstorefranchise.spring.inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import clothingstorefranchise.spring.inventory.model.ShopStock;

public interface IShopStockRepository  extends JpaRepository<ShopStock, Long>{
	
	List<ShopStock> findByShopId(Long id);
}
