package clothingstorefranchise.spring.inventory.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.facade.IWarehouseStockService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/warehouse-stocks")
@Api(value = "Endpoints to manage Warehouse Stocks")
public class WarehouseStockController {
	
	@Autowired
	private IWarehouseStockService warehouseStockService;
	
	@GetMapping("/{id}")
	public ResponseEntity<List<StockDto>> loadByWarehouseId(@PathVariable Long id) {
		return ResponseEntity.ok(warehouseStockService.findByWarehouseId(id));
	}
}
