package com.vbforge.client.dto;

import jakarta.validation.constraints.*;
import lombok.*;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutDto {
 
    @NotBlank(message = "Shipping address is required")
    @Size(max = 300, message = "Address must be at most 300 characters")
    private String shippingAddress;

}