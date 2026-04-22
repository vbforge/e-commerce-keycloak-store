package com.vbforge.client.controller;

import com.vbforge.client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Show up to 6 in-stock products as featured items on homepage
        var featured = productService.findInStock().stream().limit(6).toList();
        model.addAttribute("featuredProducts", featured);
        return "index";
    }
}