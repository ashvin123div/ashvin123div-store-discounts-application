package com.storediscounts.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.storediscounts.controller.ProductController;
import com.storediscounts.entity.Product;
import com.storediscounts.service.ProductService;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private BindingResult bindingResult;

    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService);
    }

    @Test
    public void testSave_ValidProduct() {
        // Create a sample product
        Product product = new Product();
        product.setId(1L);
        product.setProductName("Test Product");

        // Mock the behavior of the bindingResult
        when(bindingResult.hasErrors()).thenReturn(false);
        when(productService.save(product)).thenReturn(product);
        ResponseEntity<?> responseEntity = productController.save(product, bindingResult);
        verify(productService).save(product);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());
    }

    @Test
    public void testSave_InvalidProduct() {
        // Create a sample product with validation errors
        Product product = new Product();
        product.setId(1L);
        product.setProductName(null);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(getFieldErrors());
        ResponseEntity<?> responseEntity = productController.save(product, bindingResult);
        verify(productService, never()).save(product);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof List<?>);
        List<?> errors = (List<?>) responseEntity.getBody();
        assertEquals(1, errors.size());
        assertEquals("Name is required", errors.get(0));
    }

    private List<FieldError> getFieldErrors() {
        List<FieldError> fieldErrors = new ArrayList<>();
        FieldError fieldError = new FieldError("product", "name", "Name is required");
        fieldErrors.add(fieldError);
        return fieldErrors;
    }
}

