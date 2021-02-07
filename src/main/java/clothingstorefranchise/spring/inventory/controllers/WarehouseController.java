package clothingstorefranchise.spring.inventory.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clothingstorefranchise.spring.inventory.dtos.ProductInventoryDto;
import clothingstorefranchise.spring.inventory.dtos.ShopDto;
import clothingstorefranchise.spring.inventory.dtos.WarehouseDto;
import clothingstorefranchise.spring.inventory.dtos.WarehouseWithStockDto;
import clothingstorefranchise.spring.inventory.facade.IWarehouseService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/warehouses")
@Api(value = "Endpoints to manage Warehouses")
public class WarehouseController {
	
	@Autowired
	private IWarehouseService warehouseService;
	
	@PostMapping()
	public ResponseEntity<WarehouseDto> create(@Valid @RequestBody WarehouseDto warehouseDto){
		return new ResponseEntity<>(warehouseService.create(warehouseDto), HttpStatus.CREATED);
	}
	
	@GetMapping()
	public ResponseEntity<List<WarehouseDto>> loadAll(){
		return ResponseEntity.ok(warehouseService.loadAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<WarehouseWithStockDto> load(@PathVariable Long id){
		return ResponseEntity.ok(warehouseService.load(id));
	}
	
	@PutMapping
	public ResponseEntity<WarehouseDto> update(@Valid @RequestBody WarehouseDto warehouseDto){
		return ResponseEntity.ok(warehouseService.update(warehouseDto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		warehouseService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/without_product/{productId}")
	public ResponseEntity<List<WarehouseDto>> loadWarehousesWithoutProductStock(@PathVariable Long productId){
		return ResponseEntity.ok(warehouseService.findWarehouseWithoutProductStock(productId));
	}
	
	@PutMapping("/product-allocation/{productId}") 
	public ResponseEntity<ProductInventoryDto> addProductToWarehouses(@PathVariable Long productId, @RequestBody Long[] warehouseIds){
		return ResponseEntity.ok(warehouseService.addProductToWarehouses(productId, warehouseIds));
	}
}
