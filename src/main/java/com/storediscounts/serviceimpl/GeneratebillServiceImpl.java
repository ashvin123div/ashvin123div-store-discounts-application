package com.storediscounts.serviceimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.storediscounts.dto.BillDto;
import com.storediscounts.dto.ProductDto;
import com.storediscounts.entity.Product;
import com.storediscounts.entity.User;
import com.storediscounts.exception.ValidationException;
import com.storediscounts.repository.ProductRepository;
import com.storediscounts.repository.UserRepository;
import com.storediscounts.service.GeneratebillService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GeneratebillServiceImpl implements GeneratebillService{
	
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	@Override
	public BillDto generateBillWithDiscount(Long userId) {
		BillDto BillObj=null;
		User user =Optional.of(userRepository.findById(userId)).get().orElseThrow(()->
				new ValidationException("User Not Found",HttpStatus.NOT_FOUND));
		if(Objects.nonNull(user)) {
			List<Product> products=productRepository.findAll();
			List<Product> productList=getRandomProduct(products);
			if(productList.isEmpty()) {
				throw new  ValidationException("Product Not Found",HttpStatus.NOT_FOUND);
			}
			BillObj =getPercentageDiscount(user,productList);
		}
		
		return BillObj;
	}
	
	 public List<Product>
	    getRandomProduct(List<Product> products)
	    {
		   int totalItems=5;
	        Random rand = new Random();
	        List<Product> newList = new ArrayList<>();
	        for (int i = 0; i < totalItems; i++) {
	            int randomIndex = rand.nextInt(products.size());
	            newList.add(products.get(randomIndex));
	        }
	        return newList;
	    }
	
	 private BillDto getPercentageDiscount(User user,List<Product> products) {
		 double totalNonGroceriesAmount=0.0d;
		 double totalGroceriesAmount=0.0d;
		 double discount=0.0d;
		 double totalAmount=0.0d;
		 BillDto billDto=new BillDto();
		 List<ProductDto> ProductDtoList=new ArrayList<>();
		for (int i = 0; i < products.size(); i++) {
			if(products.get(i).getType().equals("groceries")) {
				 totalGroceriesAmount=totalGroceriesAmount+products.get(i).getPrice();
				 totalAmount=totalAmount+products.get(i).getPrice();
				 ProductDtoList.add(new ProductDto(products.get(i).getProductName(), products.get(i).getType(), products.get(i).getPrice()));
				 
			 }else {
				 totalNonGroceriesAmount=totalNonGroceriesAmount+products.get(i).getPrice();
				 totalAmount=totalAmount+products.get(i).getPrice();
				 ProductDtoList.add(new ProductDto(products.get(i).getProductName(), products.get(i).getType(), products.get(i).getPrice()));
			 }
		}
	
		long afterTwoYearDate=getDate(user);
		if (user.getCreateDate().getTime()>afterTwoYearDate && user.getRole().toUpperCase().equals("CUSTOMER")) {
			discount=totalNonGroceriesAmount*5/100;
		}else {
			if (user.getRole().toUpperCase().equals("EMPLOYEE")) {
				discount=totalNonGroceriesAmount*30/100;
			}
			if (user.getRole().toUpperCase().equals("AFFILIATE")) {
				discount=totalNonGroceriesAmount*10/100;
			}
		}
		
		if(totalAmount>100) {
			double dollarDiscount=getAmountDiscount(totalAmount);
			discount=discount+dollarDiscount;
		}
		billDto.setCustomerName(user.getUserName());
		billDto.setDate(new Date());
		billDto.setBillNo(generateBillNumber());
		billDto.setCustomerAddress(user.getAddress());
		billDto.setTotalAmount(totalAmount);
		billDto.setDiscount(discount);
		if(discount>0) {
			billDto.setTotalNetPay(totalAmount-discount);
		}else {
			billDto.setTotalNetPay(totalAmount);
		}
		
		billDto.setProducts(ProductDtoList);
		return billDto;
	 }

	  private double getAmountDiscount(double totalAmount) {
	        return Math.floor(totalAmount / 100) * 5;
	  }
	  
	  public String generateBillNumber() {
	        Random random = new Random();
	        int billNumber = random.nextInt(1000000); 
	        String formattedBillNumber = String.format("%06d", billNumber);
	        String generatedBillNumber = "BILL" + formattedBillNumber;
	        return generatedBillNumber;
	    }
	  
	  public long getDate(User user) {
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(user.getCreateDate());
		  cal.add(Calendar.YEAR, 2); 
		  Date date = cal.getTime();
		  System.out.println(date);
		  return cal.getTimeInMillis();
	  }

}
