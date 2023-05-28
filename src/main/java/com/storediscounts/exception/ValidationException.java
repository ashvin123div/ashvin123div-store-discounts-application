package com.storediscounts.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
@Getter
public class ValidationException extends RuntimeException{
	
  private HttpStatus httpStatus;

	public ValidationException(String message,HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
		
	}
  
}
