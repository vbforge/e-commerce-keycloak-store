package com.vbforge.client.controller;

import com.vbforge.client.dto.RegisterDto;
import com.vbforge.client.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerDto") RegisterDto dto,
                           BindingResult result,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        if (!dto.passwordsMatch()) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "register";
        }
        try {
            userService.register(dto);
            redirectAttributes.addFlashAttribute("success", "Account created! You can now sign in.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("registerError", e.getMessage());
            return "register";
        }
    }
}