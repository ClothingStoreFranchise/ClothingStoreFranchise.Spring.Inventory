package clothingstorefranchise.spring.inventory.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clothingstorefranchise.spring.inventory.dtos.ShopDto;
import clothingstorefranchise.spring.inventory.dtos.StockDto;
import clothingstorefranchise.spring.inventory.facade.IShopService;
import clothingstorefranchise.spring.inventory.facade.IShopStockService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/shop-stocks")
@Api(value = "Endpoints to manage Shop Stocks")
public class ShopStockController {
	
	@Autowired
	private IShopStockService shopStockService;
	
	@Autowired
	private IShopService shopService;
	
	@GetMapping("/{shopId}")
	public ResponseEntity<List<StockDto>> loadByShopId(@PathVariable Long shopId) {
		return ResponseEntity.ok(shopStockService.findByShopId(shopId));
	}
}
