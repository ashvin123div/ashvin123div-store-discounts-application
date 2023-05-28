package com.storediscounts.serviceimpl;

import org.springframework.stereotype.Service;

import com.storediscounts.entity.Product;
import com.storediscounts.repository.ProductRepository;
import com.storediscounts.service.ProductService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	
	@Override
	public Product save(Product product) {
		product.setId(null);
		return productRepository.save(product);
	}

}
