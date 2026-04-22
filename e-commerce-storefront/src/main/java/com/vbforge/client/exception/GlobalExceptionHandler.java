package com.vbforge.client.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(CartException.class)
    public String handleCart(CartException ex, Model model) {
        model.addAttribute("errorTitle", "Cart Error");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(OrderException.class)
    public String handleOrder(OrderException ex, Model model) {
        model.addAttribute("errorTitle", "Order Error");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Unexpected Error");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}