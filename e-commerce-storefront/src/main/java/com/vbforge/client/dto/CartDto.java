package com.vbforge.client.dto;

import lombok.*;
 
import java.util.List;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
 
    private Long cartId;
    private List<CartItemDto> items;
    private Double totalPrice;
    private Integer totalItems;
 
    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

}