package com.vbforge.client.service;

import com.vbforge.client.dto.ProductDto;
import com.vbforge.client.entity.Product;
import com.vbforge.client.exception.ResourceNotFoundException;
import com.vbforge.client.mapper.ProductMapper;
import com.vbforge.client.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> findAll() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    public List<ProductDto> findByCategory(Product.ProductCategory category) {
        return productMapper.toDtoList(productRepository.findByCategory(category));
    }

    public List<ProductDto> findInStock() {
        return productMapper.toDtoList(productRepository.findByStockGreaterThan(0));
    }

    public ProductDto findById(Long id) {
        return productRepository.findById(id)
            .map(productMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    // Internal use — returns entity for cart/order operations
    public Product findEntityById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }
}