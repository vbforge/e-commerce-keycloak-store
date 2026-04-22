package com.vbforge.client.controller;

import com.vbforge.client.dto.CheckoutDto;
import com.vbforge.client.dto.OrderDto;
import com.vbforge.client.entity.User;
import com.vbforge.client.service.CartService;
import com.vbforge.client.service.CheckoutService;
import com.vbforge.client.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final CartService cartService;
    private final UserService userService;

    public CheckoutController(CheckoutService checkoutService,
                               CartService cartService,
                               UserService userService) {
        this.checkoutService = checkoutService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public String checkoutPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        var cart = cartService.getCartForUser(user);

        if (cart.isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("checkoutDto", new CheckoutDto());
        return "checkout";
    }

    @PostMapping("/confirm")
    public String confirmOrder(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid @ModelAttribute("checkoutDto") CheckoutDto dto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(userDetails.getUsername());

        if (result.hasErrors()) {
            model.addAttribute("cart", cartService.getCartForUser(user));
            return "checkout";
        }

        OrderDto order = checkoutService.placeOrder(user, dto);
        redirectAttributes.addFlashAttribute("placedOrder", order);
        return "redirect:/checkout/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmation(@ModelAttribute("placedOrder") OrderDto placedOrder, Model model) {
        if (placedOrder == null || placedOrder.getId() == null) {
            return "redirect:/";
        }
        model.addAttribute("order", placedOrder);
        return "order-confirmation";
    }
}