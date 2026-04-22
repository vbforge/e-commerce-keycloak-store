package com.vbforge.client.service;

import com.vbforge.client.dto.CartDto;
import com.vbforge.client.entity.Cart;
import com.vbforge.client.entity.CartItem;
import com.vbforge.client.entity.Product;
import com.vbforge.client.entity.User;
import com.vbforge.client.exception.CartException;
import com.vbforge.client.mapper.CartMapper;
import com.vbforge.client.repository.CartItemRepository;
import com.vbforge.client.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
    }

    public CartDto getCartForUser(User user) {
        Cart cart = getOrCreateCart(user);
        return cartMapper.toDto(cart);
    }

    public void addItem(User user, Product product, int quantity) {
        if (quantity < 1) {
            throw new CartException("Quantity must be at least 1");
        }
        if (product.getStock() < quantity) {
            throw new CartException("Only " + product.getStock() + " units of '" + product.getName() + "' in stock");
        }

        Cart cart = getOrCreateCart(user);

        cartItemRepository.findByCartAndProduct(cart, product)
            .ifPresentOrElse(
                existing -> {
                    int newQty = existing.getQuantity() + quantity;
                    if (newQty > product.getStock()) {
                        throw new CartException("Cannot add " + quantity + " more — only "
                            + (product.getStock() - existing.getQuantity()) + " additional units available");
                    }
                    existing.setQuantity(newQty);
                    cartItemRepository.save(existing);
                },
                () -> {
                    CartItem item = CartItem.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(quantity)
                        .priceAtAdd(product.getPrice())
                        .build();
                    cart.getItems().add(item);
                    cartItemRepository.save(item);
                }
            );
    }

    public void updateQuantity(User user, Long cartItemId, int quantity) {
        Cart cart = getOrCreateCart(user);
        CartItem item = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new CartException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new CartException("Cart item does not belong to current user");
        }
        if (quantity < 1) {
            throw new CartException("Quantity must be at least 1. Use remove to delete the item.");
        }
        if (quantity > item.getProduct().getStock()) {
            throw new CartException("Only " + item.getProduct().getStock() + " units available");
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    public void removeItem(User user, Long cartItemId) {
        Cart cart = getOrCreateCart(user);
        CartItem item = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new CartException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new CartException("Cart item does not belong to current user");
        }

        cart.getItems().remove(item);
        cartItemRepository.delete(item);
    }

    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    // Returns the raw entity — used internally by CheckoutService
    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
            .orElseGet(() -> cartRepository.save(
                Cart.builder().user(user).build()
            ));
    }
}