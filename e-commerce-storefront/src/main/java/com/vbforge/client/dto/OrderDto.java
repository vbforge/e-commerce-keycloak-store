package com.vbforge.client.dto;

import com.vbforge.client.entity.Order;
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
    private List<OrderItemDto> items;
    private Double totalPrice;
    private Order.OrderStatus status;
    private String shippingAddress;
    private LocalDateTime createdAt;

}