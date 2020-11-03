package clothingstorefranchise.spring.inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import clothingstorefranchise.spring.inventory.model.Shop;

public interface IShopRepository extends JpaRepository<Shop, Long> {
	
	@Query("select distinct s from Shop s JOIN FETCH s.shopStocks t where t.id.productId = ?1")
	List<Shop> findShopsWithProductStocksByProductId(Long productId);
	
	List<Shop> findByIdNotIn(List<Long> ids);
	
	@Query("select distinct s from Shop s where s.id not in (select t.id.shopId from ShopStock t where t.id.productId = ?1)")
	List<Shop> findShopsNotMatchProductId(Long productId);
}
