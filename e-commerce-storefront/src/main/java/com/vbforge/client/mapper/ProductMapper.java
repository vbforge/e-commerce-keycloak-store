package com.vbforge.client.mapper;

import com.vbforge.client.dto.ProductDto;
import com.vbforge.client.entity.Product;
import org.springframework.stereotype.Component;
 
import java.util.List;
 
@Component
public class ProductMapper {
 
    public ProductDto toDto(Product product) {
        if (product == null) return null;
        return ProductDto.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .description(product.getDescription())
            .category(product.getCategory())
            .stock(product.getStock())
            .build();
    }
 
    public List<ProductDto> toDtoList(List<Product> products) {
        return products.stream().map(this::toDto).toList();
    }

}