package com.storediscounts.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.storediscounts.entity.Product;
import com.storediscounts.repository.ProductRepository;
import com.storediscounts.service.ProductService;
import com.storediscounts.serviceimpl.ProductServiceImpl;

public class ProductServiceImplTest {
    
    @Mock
    private ProductRepository productRepository;
    
    private ProductService productService;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
    }
    
    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setProductName("Test Product");
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.save(product);
        verify(productRepository).save(product);
        
        assertEquals(product, savedProduct);
    }
}

