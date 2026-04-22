package com.vbforge.client.service;

import com.vbforge.client.dto.CheckoutDto;
import com.vbforge.client.dto.OrderDto;
import com.vbforge.client.entity.*;
import com.vbforge.client.exception.OrderException;
import com.vbforge.client.mapper.OrderMapper;
import com.vbforge.client.repository.OrderRepository;
import com.vbforge.client.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CheckoutService {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public CheckoutService(CartService cartService,
                           OrderRepository orderRepository,
                           ProductRepository productRepository,
                           OrderMapper orderMapper) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    public OrderDto placeOrder(User user, CheckoutDto checkoutDto) {
        Cart cart = cartService.getOrCreateCart(user);

        if (cart.getItems().isEmpty()) {
            throw new OrderException("Cannot place an order with an empty cart");
        }

        // Validate stock for every item before committing anything
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new OrderException("Insufficient stock for '" + product.getName()
                    + "'. Available: " + product.getStock()
                    + ", requested: " + cartItem.getQuantity());
            }
        }

        // Build order
        Order order = Order.builder()
            .user(user)
            .shippingAddress(checkoutDto.getShippingAddress())
            .status(Order.OrderStatus.PENDING)
            .items(new ArrayList<>())
            .build();

        double total = 0.0;
        List<Product> productsToUpdate = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();

            OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(cartItem.getQuantity())
                .priceAtPurchase(cartItem.getPriceAtAdd())
                .build();

            order.getItems().add(orderItem);
            total += orderItem.getSubtotal();

            // Decrement stock
            product.setStock(product.getStock() - cartItem.getQuantity());
            productsToUpdate.add(product);
        }

        order.setTotalPrice(total);
        Order saved = orderRepository.save(order);

        // Persist stock changes
        productRepository.saveAll(productsToUpdate);

        // Clear the cart
        cartService.clearCart(user);

        return orderMapper.toDto(saved);
    }
}