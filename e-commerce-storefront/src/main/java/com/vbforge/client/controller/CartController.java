package com.vbforge.client.controller;

import com.vbforge.client.entity.User;
import com.vbforge.client.service.CartService;
import com.vbforge.client.service.ProductService;
import com.vbforge.client.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    public CartController(CartService cartService,
                          ProductService productService,
                          UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("cart", cartService.getCartForUser(user));
        return "cart";
    }

    @PostMapping("/add")
    public String addItem(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam Long productId,
                          @RequestParam(defaultValue = "1") int quantity,
                          RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(userDetails.getUsername());
        var product = productService.findEntityById(productId);
        cartService.addItem(user, product, quantity);
        redirectAttributes.addFlashAttribute("cartSuccess", "'" + product.getName() + "' added to cart.");
        return "redirect:/products/" + productId;
    }

    @PostMapping("/update")
    public String updateQuantity(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam Long cartItemId,
                                 @RequestParam int quantity,
                                 RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(userDetails.getUsername());
        cartService.updateQuantity(user, cartItemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeItem(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam Long cartItemId,
                             RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(userDetails.getUsername());
        cartService.removeItem(user, cartItemId);
        redirectAttributes.addFlashAttribute("cartSuccess", "Item removed from cart.");
        return "redirect:/cart";
    }
}