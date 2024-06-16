package com.example.ecommerceportal.integration;

import com.example.ecommerceportal.model.Product;
import com.example.ecommerceportal.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void init() {
        productRepository.deleteAll();
        productRepository.save(new Product("Product1", "Category1", 40, 1));
        productRepository.save(new Product("Product2", "Category2", 30, 2.5));
    }

    @Test
    public void testGetAllProducts() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity("/api/products", Product[].class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).extracting(Product::getName).containsExactlyInAnyOrder("Product1", "Product2");
    }

    @Test
    public void testAddProduct() {
        Product newProduct = new Product("Product3", "Category3", 50, 3.5);
        ResponseEntity<Product> response = restTemplate.postForEntity("/api/products", newProduct, Product.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        Product savedProduct = response.getBody();
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Product3");
    }

    @Test
    public void testUpdateProduct() {
        Product existingProduct = productRepository.findAll().get(0);
        Long productId = existingProduct.getId();

        Product updatedProduct = new Product("UpdatedProduct", "UpdatedCategory", 60, 4.5);
        HttpEntity<Product> requestEntity = new HttpEntity<>(updatedProduct);

        ResponseEntity<Product> response = restTemplate.exchange("/api/products/" + productId, HttpMethod.PUT, requestEntity, Product.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        Product savedProduct = response.getBody();
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("UpdatedProduct");

        ResponseEntity<Product[]> allProductsResponse = restTemplate.getForEntity("/api/products", Product[].class);
        assertThat(allProductsResponse.getBody()).extracting(Product::getName).contains("UpdatedProduct");
    }
}
