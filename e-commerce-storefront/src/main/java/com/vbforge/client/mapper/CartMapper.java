package com.vbforge.client.mapper;

import com.vbforge.client.dto.CartDto;
import com.vbforge.client.dto.CartItemDto;
import com.vbforge.client.entity.Cart;
import com.vbforge.client.entity.CartItem;
import org.springframework.stereotype.Component;
 
@Component
public class CartMapper {
 
    public CartItemDto toItemDto(CartItem item) {
        if (item == null) return null;
        return CartItemDto.builder()
            .cartItemId(item.getId())
            .productId(item.getProduct().getId())
            .productName(item.getProduct().getName())
            .unitPrice(item.getPriceAtAdd())
            .quantity(item.getQuantity())
            .subtotal(item.getSubtotal())
            .build();
    }
 
    public CartDto toDto(Cart cart) {
        if (cart == null) return null;
        var itemDtos = cart.getItems().stream()
            .map(this::toItemDto)
            .toList();
        return CartDto.builder()
            .cartId(cart.getId())
            .items(itemDtos)
            .totalPrice(cart.getTotalPrice())
            .totalItems(cart.getTotalItems())
            .build();
    }
}