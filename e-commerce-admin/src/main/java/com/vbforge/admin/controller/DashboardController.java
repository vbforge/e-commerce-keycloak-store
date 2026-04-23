package com.vbforge.admin.controller;

import com.vbforge.admin.service.DashboardService;
import com.vbforge.admin.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DashboardController {

    private final DashboardService dashboardService;
    private final OrderService orderService;

    public DashboardController(DashboardService dashboardService, OrderService orderService) {
        this.dashboardService = dashboardService;
        this.orderService = orderService;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("stats", dashboardService.buildStats());
        model.addAttribute("recentOrders", orderService.findAll().stream().limit(8).toList());
        return "dashboard";
    }
}