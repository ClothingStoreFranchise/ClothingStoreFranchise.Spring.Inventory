package clothingstorefranchise.spring.inventory.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clothingstorefranchise.spring.inventory.dtos.ProductDto;
import clothingstorefranchise.spring.inventory.dtos.ProductInventoryDto;
import clothingstorefranchise.spring.inventory.facade.IProductService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/products")
@Api(value = "Endpoints to manage Products")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> loadAll() {
		return ResponseEntity.ok(productService.loadAll());
	}
	
	@GetMapping("/{productId}/stocks")
	public ResponseEntity<ProductInventoryDto> loadProductStock(@PathVariable Long productId){
		
		return ResponseEntity.ok(productService.loadProductStock(productId));
	}
	
	@GetMapping("/{productId}/stocks-without-warehouses")
	public ResponseEntity<ProductInventoryDto> loadProductStockWithoutWarehouses(@PathVariable Long productId){
		
		return ResponseEntity.ok(productService.loadProductStockWithoutWarehouse(productId)); 
	}
}
