package clothingstorefranchise.spring.inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import clothingstorefranchise.spring.inventory.model.WarehouseStock;

public interface IWarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {
	
	@Query(nativeQuery=true, 
			value="select * from warehouse_stock w where w.building_id = ?1 order by w.product_id, w.size asc")
	List<WarehouseStock> findByWarehouseIdOrderByIdSizeAscQuery(Long warehouseId);
	
	@Query("select new clothingstorefranchise.spring.inventory.dtos.StockCountDto(w.id.productId, w.id.size, sum(w.stock))"
			+ "from WarehouseStock w where w.id.productId in ?1 group by w.id.size")
	List<StockCountDto> countTotalProductStocks(List<Long> productIds);
	
	@Query("select new clothingstorefranchise.spring.inventory.dtos.StockCountDto(w.id.productId, w.id.size, sum(w.stock))"
			+ "from WarehouseStock w where w.id.productId = ?1 and w.id.size = ?2 group by w.id.size")
	StockCountDto countTotalProductStockByProductIdAndSize(Long productId, int size);
	
	@Query("select w from WarehouseStock w where w.id.productId = ?1 and w.id.size = ?2 and w.stock >= ?3 order by w.stock asc")
	List<WarehouseStock> findByProductIdAndSizeWhereStockIsGreaterThanQuantityAndStockIsMax(Long productId, int size, Long quantity);
	
	@Transactional
	@Modifying
	@Query("delete from WarehouseStock w WHERE w.id.productId = ?1")
	void deleteStockByProductId(Long productId);
}
