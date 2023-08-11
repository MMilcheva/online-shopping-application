package com.example.productservice.controller;

import com.example.productservice.dto.ProductRequestDtoIn;
import com.example.productservice.dto.ProductResponseDtoOut;
import com.example.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequestDtoIn productRequestDtoIn) {
        productService.createProduct(productRequestDtoIn);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDtoOut> getAllProducts() {
        return productService.getAllProducts();
    }

}
