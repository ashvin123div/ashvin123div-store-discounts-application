package com.storediscounts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@SequenceGenerator(name = "product_id_sequence",
    sequenceName = "product_id_sequence",initialValue = 1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_id_sequence")
	private Long Id;
	@NotBlank(message = "Product Name is required field !!")
	private String productName;
	@NotBlank(message = "Product Type is required field !!")
	private String type;
	@NotNull(message = "Product Price is required field !!")
	private double price;
	public Product( String productName,String type,double price) {
		super();
		this.productName = productName;
		this.type = type;
		this.price = price;
	}
	
	
}
