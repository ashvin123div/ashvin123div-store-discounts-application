package com.storediscounts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.storediscounts.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	//@Query(value="SELECT * FROM store_discounts_application.product p ORDER BY RAND() LIMIT 3",nativeQuery = true)
	//public List<Product> findAllProduct();

}
