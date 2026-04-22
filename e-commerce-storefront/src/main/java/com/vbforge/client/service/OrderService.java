package com.vbforge.client.service;

import com.vbforge.client.dto.OrderDto;
import com.vbforge.client.entity.Order;
import com.vbforge.client.entity.User;
import com.vbforge.client.exception.ResourceNotFoundException;
import com.vbforge.client.mapper.OrderMapper;
import com.vbforge.client.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDto> findOrdersForUser(User user) {
        return orderMapper.toDtoList(
            orderRepository.findByUserOrderByCreatedAtDesc(user)
        );
    }

    public OrderDto findByIdForUser(Long orderId, User user) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Order not found: " + orderId);
        }
        return orderMapper.toDto(order);
    }
}