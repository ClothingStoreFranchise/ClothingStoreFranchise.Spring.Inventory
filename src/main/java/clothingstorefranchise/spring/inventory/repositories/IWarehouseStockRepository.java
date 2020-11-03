package clothingstorefranchise.spring.inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import clothingstorefranchise.spring.inventory.dtos.StockCountDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.model.WarehouseStock;

public interface IWarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {
	
	@Query(nativeQuery=true, 
			value="select * from warehouse_stock w where w.warehouse_id = ?1 order by w.product_id, w.size asc")
	List<WarehouseStock> findByWarehouseIdOrderByIdSizeAscQuery(Long warehouseId);
	
	//@Query("select w from WarehouseStock w where w.id.productId = ?1 group by w.id.size")
	@Query("select new clothingstorefranchise.spring.inventory.dtos.StockCountDto(w.id.size, sum(w.stock))"
			+ "from WarehouseStock w where w.id.productId = ?1 group by w.id.size")
	List<StockCountDto> countTotalProductStocks(Long productId);
}
