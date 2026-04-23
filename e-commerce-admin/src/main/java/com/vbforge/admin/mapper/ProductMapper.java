package com.vbforge.admin.mapper;

import com.vbforge.admin.dto.ProductDto;
import com.vbforge.admin.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductDto toDto(Product p) {
        if (p == null) return null;
        return ProductDto.builder()
            .id(p.getId())
            .name(p.getName())
            .price(p.getPrice())
            .description(p.getDescription())
            .category(p.getCategory())
            .stock(p.getStock())
            .build();
    }

    public List<ProductDto> toDtoList(List<Product> products) {
        return products.stream().map(this::toDto).toList();
    }

    public void applyDto(ProductDto dto, Product product) {
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setStock(dto.getStock());
    }
}