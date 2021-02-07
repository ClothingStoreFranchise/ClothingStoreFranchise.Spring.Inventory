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

import clothingstorefranchise.spring.inventory.dtos.ShopDto;
import clothingstorefranchise.spring.inventory.dtos.ShopWithStockDto;
import clothingstorefranchise.spring.inventory.facade.IShopService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/shops")
@Api(value = "Endpoints to manage Shops")
public class ShopController {

	@Autowired
	private IShopService shopService;
	
	@PostMapping
	public ResponseEntity<ShopDto> create(@Valid @RequestBody ShopDto shopDto) {
		return new ResponseEntity<>(shopService.create(shopDto), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ShopDto>> loadAll() {
		return ResponseEntity.ok(shopService.loadAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ShopWithStockDto> load(@PathVariable Long id){
		return ResponseEntity.ok(shopService.load(id));
	}
	
	@PutMapping
	public ResponseEntity<ShopDto> update(@Valid @RequestBody ShopDto shopDto){
		return ResponseEntity.ok(shopService.update(shopDto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		shopService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/without_product/{productId}")
	public ResponseEntity<List<ShopDto>> loadShopsWithoutProductStock(@PathVariable Long productId){
		return ResponseEntity.ok(shopService.findShopsWithoutProductStock(productId));
	}
	
	
	@PutMapping("/product-allocation/{productId}") 
	public ResponseEntity<List<ShopWithStockDto>> addProductToShops(@PathVariable Long productId, @RequestBody Long[] shopIds){

		return ResponseEntity.ok(this.shopService.addProductToShops(productId, shopIds));
	}
}
