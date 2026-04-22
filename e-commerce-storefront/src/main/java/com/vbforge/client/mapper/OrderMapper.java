package com.vbforge.client.mapper;

import com.vbforge.client.dto.OrderDto;
import com.vbforge.client.dto.OrderItemDto;
import com.vbforge.client.entity.Order;
import com.vbforge.client.entity.OrderItem;
import org.springframework.stereotype.Component;
 
import java.util.List;
 
@Component
public class OrderMapper {
 
    public OrderItemDto toItemDto(OrderItem item) {
        if (item == null) return null;
        return OrderItemDto.builder()
            .orderItemId(item.getId())
            .productId(item.getProduct().getId())
            .productName(item.getProduct().getName())
            .unitPrice(item.getPriceAtPurchase())
            .quantity(item.getQuantity())
            .subtotal(item.getSubtotal())
            .build();
    }
 
    public OrderDto toDto(Order order) {
        if (order == null) return null;
        var itemDtos = order.getItems().stream()
            .map(this::toItemDto)
            .toList();
        return OrderDto.builder()
            .id(order.getId())
            .items(itemDtos)
            .totalPrice(order.getTotalPrice())
            .status(order.getStatus())
            .shippingAddress(order.getShippingAddress())
            .createdAt(order.getCreatedAt())
            .build();
    }
 
    public List<OrderDto> toDtoList(List<Order> orders) {
        return orders.stream().map(this::toDto).toList();
    }

}