package clothingstorefranchise.spring.inventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import clothingstorefranchise.spring.inventory.model.Warehouse;

public interface IWarehouseRepository extends JpaRepository<Warehouse, Long> {
	
	@Query("select distinct w from Warehouse w JOIN FETCH w.warehouseStocks t where t.id.productId = ?1")
	List<Warehouse> findWarehousesWithProductStocksByProductId(Long productId);
			
	@Query("select distinct w from Warehouse w where w.id not in (select t.id.buildingId from WarehouseStock t where t.id.productId = ?1)")
	List<Warehouse> findWarehousesNotMatchProductId(Long productId);
}
