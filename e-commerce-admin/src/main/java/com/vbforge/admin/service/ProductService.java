package com.vbforge.admin.service;

import com.vbforge.admin.dto.ProductDto;
import com.vbforge.admin.entity.Product;
import com.vbforge.admin.exception.ResourceNotFoundException;
import com.vbforge.admin.mapper.ProductMapper;
import com.vbforge.admin.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return productRepository.findById(id)
            .map(productMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    public ProductDto create(ProductDto dto) {
        Product product = Product.builder()
            .name(dto.getName())
            .price(dto.getPrice())
            .description(dto.getDescription())
            .category(dto.getCategory())
            .stock(dto.getStock())
            .build();
        return productMapper.toDto(productRepository.save(product));
    }

    public ProductDto update(Long id, ProductDto dto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
        productMapper.applyDto(dto, product);
        return productMapper.toDto(productRepository.save(product));
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return productRepository.count();
    }
}