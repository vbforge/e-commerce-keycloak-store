package com.vbforge.admin.service;

import com.vbforge.admin.dto.OrderDto;
import com.vbforge.admin.entity.Order;
import com.vbforge.admin.exception.AdminException;
import com.vbforge.admin.exception.ResourceNotFoundException;
import com.vbforge.admin.mapper.OrderMapper;
import com.vbforge.admin.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        return orderMapper.toDtoList(orderRepository.findAllByOrderByCreatedAtDesc());
    }

    @Transactional(readOnly = true)
    public List<OrderDto> findByStatus(Order.OrderStatus status) {
        return orderMapper.toDtoList(orderRepository.findByStatus(status));
    }

    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        return orderRepository.findById(id)
            .map(orderMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
    }

    public OrderDto updateStatus(Long id, Order.OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));

        // Guard against invalid transitions
        if (order.getStatus() == Order.OrderStatus.DELIVERED
                || order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new AdminException("Cannot change status of a "
                + order.getStatus().name().toLowerCase() + " order");
        }
        order.setStatus(newStatus);
        return orderMapper.toDto(orderRepository.save(order));
    }

    public void delete(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long count() { return orderRepository.count(); }

    @Transactional(readOnly = true)
    public long countByStatus(Order.OrderStatus status) { return orderRepository.countByStatus(status); }

    @Transactional(readOnly = true)
    public double totalRevenue() { return orderRepository.sumRevenue(); }
}