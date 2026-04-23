package com.vbforge.admin.dto;

import com.vbforge.admin.entity.Order;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private String customerUsername;
    private String customerEmail;
    private List<OrderItemDto> items;
    private Double totalPrice;
    private Order.OrderStatus status;
    private String shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}