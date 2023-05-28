package com.storediscounts.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.storediscounts.entity.Product;
import com.storediscounts.service.ProductService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/product")
@Slf4j
@AllArgsConstructor
public class ProductController {
	
	private final ProductService productService;

	@PostMapping("/save")
	public ResponseEntity<?> save(
			@Valid @RequestBody Product product,BindingResult bindingResult) {
		log.info("create product method {}");
		if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
		return new ResponseEntity<Product>(productService.save(product),
				HttpStatus.OK);
	}
}
