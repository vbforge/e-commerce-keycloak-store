package com.vbforge.admin.service;

import com.vbforge.admin.dto.DashboardStatsDto;
import com.vbforge.admin.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;

    public DashboardService(ProductService productService,
                            OrderService orderService,
                            UserService userService) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }

    public DashboardStatsDto buildStats() {
        return DashboardStatsDto.builder()
            .totalProducts(productService.count())
            .totalUsers(userService.count())
            .totalOrders(orderService.count())
            .pendingOrders(orderService.countByStatus(Order.OrderStatus.PENDING))
            .confirmedOrders(orderService.countByStatus(Order.OrderStatus.CONFIRMED))
            .shippedOrders(orderService.countByStatus(Order.OrderStatus.SHIPPED))
            .deliveredOrders(orderService.countByStatus(Order.OrderStatus.DELIVERED))
            .cancelledOrders(orderService.countByStatus(Order.OrderStatus.CANCELLED))
            .totalRevenue(orderService.totalRevenue())
            .build();
    }
}