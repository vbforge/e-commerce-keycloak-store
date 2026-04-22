package com.vbforge.client.dto;

import com.vbforge.client.entity.Product;
import lombok.*;
 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
 
    private Long id;
    private String name;
    private Double price;
    private String description;
    private Product.ProductCategory category;
    private Integer stock;
 
    public boolean isInStock() {
        return stock != null && stock > 0;
    }

}