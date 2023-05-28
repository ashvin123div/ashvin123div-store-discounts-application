package com.storediscounts.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDto {
	private String billNo;
	private Date date;
	private String customerName;
	private String customerAddress;
	private double totalNetPay;
	private double discount;
	private double totalAmount;
	private List<ProductDto> products;
	
}
