package clothingstorefranchise.spring.inventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import clothingstorefranchise.spring.inventory.model.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {

}
