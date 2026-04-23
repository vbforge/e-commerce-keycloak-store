package com.vbforge.admin.controller;

import com.vbforge.admin.dto.ProductDto;
import com.vbforge.admin.entity.Product;
import com.vbforge.admin.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", Product.ProductCategory.values());
        model.addAttribute("productDto", new ProductDto());
        return "products";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories", Product.ProductCategory.values());
        model.addAttribute("productDto", productService.findById(id));
        return "products";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("productDto") ProductDto dto,
                       BindingResult result,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("products", productService.findAll());
            model.addAttribute("categories", Product.ProductCategory.values());
            return "products";
        }
        if (dto.getId() != null) {
            productService.update(dto.getId(), dto);
            redirectAttributes.addFlashAttribute("success", "Product updated.");
        } else {
            productService.create(dto);
            redirectAttributes.addFlashAttribute("success", "Product created.");
        }
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Product deleted.");
        return "redirect:/products";
    }
}