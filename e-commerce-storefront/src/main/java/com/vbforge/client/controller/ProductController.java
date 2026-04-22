package com.vbforge.client.controller;

import com.vbforge.client.entity.Product;
import com.vbforge.client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) Product.ProductCategory category,
                       Model model) {
        var products = (category != null)
            ? productService.findByCategory(category)
            : productService.findAll();

        model.addAttribute("products", products);
        model.addAttribute("categories", Product.ProductCategory.values());
        model.addAttribute("selectedCategory", category);
        return "products";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product-detail";
    }
}