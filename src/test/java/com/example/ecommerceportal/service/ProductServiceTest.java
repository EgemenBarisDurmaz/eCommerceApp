package com.example.ecommerceportal.service;

import com.example.ecommerceportal.model.Product;
import com.example.ecommerceportal.repository.ProductRepository;
import com.example.ecommerceportal.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Product createProduct(Long id, String name, String category, double price, double rating) {
        Product product = new Product(name, category, price, rating);
        product.setId(id);
        return product;
    }

    @Test
    public void testFindById() {
        Product product = createProduct(1L, "Test Product", "Test Category", 100.0, 4.5);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Optional<Product> foundProduct = productService.findById(product.getId());

        assertTrue(foundProduct.isPresent());
        assertEquals(product.getName(), foundProduct.get().getName());
        assertEquals(product.getCategory(), foundProduct.get().getCategory());
        assertEquals(product.getPrice(), foundProduct.get().getPrice());
        assertEquals(product.getRating(), foundProduct.get().getRating());
    }

    @Test
    public void testSaveProduct() {
        Product product = createProduct(2L, "New Product", "New Category", 80.0, 3.5);
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);

        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getCategory(), savedProduct.getCategory());
        assertEquals(product.getPrice(), savedProduct.getPrice());
        assertEquals(product.getRating(), savedProduct.getRating());
        verify(productRepository, times(1)).save(product);
    }
}
