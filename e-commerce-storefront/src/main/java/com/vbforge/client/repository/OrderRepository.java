package com.vbforge.client.repository;

import com.vbforge.client.entity.Order;
import com.vbforge.client.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.List;
 
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserOrderByCreatedAtDesc(User user);
    List<Order> findByUserUsernameOrderByCreatedAtDesc(String username);

}