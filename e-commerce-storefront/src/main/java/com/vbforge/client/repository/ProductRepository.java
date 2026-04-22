package com.vbforge.client.repository;

import com.vbforge.client.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Product.ProductCategory category);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByStockGreaterThan(int stock);

}
