package com.vbforge.client.dto;

import lombok.*;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
 
    private Long cartItemId;
    private Long productId;
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    private Double subtotal;

}