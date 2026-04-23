package com.vbforge.admin.mapper;

import com.vbforge.admin.dto.OrderDto;
import com.vbforge.admin.dto.OrderItemDto;
import com.vbforge.admin.entity.Order;
import com.vbforge.admin.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderItemDto toItemDto(OrderItem item) {
        if (item == null) return null;
        return OrderItemDto.builder()
            .id(item.getId())
            .productId(item.getProduct().getId())
            .productName(item.getProduct().getName())
            .quantity(item.getQuantity())
            .unitPrice(item.getPriceAtPurchase())
            .subtotal(item.getSubtotal())
            .build();
    }

    public OrderDto toDto(Order order) {
        if (order == null) return null;
        return OrderDto.builder()
            .id(order.getId())
            .customerUsername(order.getUser().getUsername())
            .customerEmail(order.getUser().getEmail())
            .items(order.getItems().stream().map(this::toItemDto).toList())
            .totalPrice(order.getTotalPrice())
            .status(order.getStatus())
            .shippingAddress(order.getShippingAddress())
            .createdAt(order.getCreatedAt())
            .updatedAt(order.getUpdatedAt())
            .build();
    }

    public List<OrderDto> toDtoList(List<Order> orders) {
        return orders.stream().map(this::toDto).toList();
    }
}