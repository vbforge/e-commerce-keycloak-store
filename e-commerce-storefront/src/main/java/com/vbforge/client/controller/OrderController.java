package com.vbforge.client.controller;

import com.vbforge.client.entity.User;
import com.vbforge.client.service.OrderService;
import com.vbforge.client.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String orderHistory(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("orders", orderService.findOrdersForUser(user));
        return "orders";
    }

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetails userDetails,
                              Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("order", orderService.findByIdForUser(id, user));
        return "order-detail";
    }
}
