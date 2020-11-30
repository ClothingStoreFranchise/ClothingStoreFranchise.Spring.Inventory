package clothingstorefranchise.spring.inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import clothingstorefranchise.spring.inventory.model.ShopStock;

public interface IShopStockRepository  extends JpaRepository<ShopStock, Long>{
	
	List<ShopStock> findByShopId(Long id);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM ShopStock s WHERE s.id.productId = ?1")
	void deleteStockByProductId(Long productId);
}
