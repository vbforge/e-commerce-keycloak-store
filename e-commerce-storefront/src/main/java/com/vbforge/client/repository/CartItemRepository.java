package com.vbforge.client.repository;

import com.vbforge.client.entity.Cart;
import com.vbforge.client.entity.CartItem;
import com.vbforge.client.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.Optional;
 
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

}