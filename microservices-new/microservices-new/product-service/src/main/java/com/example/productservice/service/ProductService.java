package com.example.productservice.service;

import com.example.productservice.dto.ProductRequestDtoIn;
import com.example.productservice.dto.ProductResponseDtoOut;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequestDtoIn productRequestDtoIn) {
        Product product = Product.builder()
                .name(productRequestDtoIn.getName())
                .description(productRequestDtoIn.getDescription())
                .price(productRequestDtoIn.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product with id {} is saved", product.getId());
    }

    public List<ProductResponseDtoOut> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponseDtoOut).toList();
    }

    private ProductResponseDtoOut mapToProductResponseDtoOut(Product product) {

        return ProductResponseDtoOut.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
