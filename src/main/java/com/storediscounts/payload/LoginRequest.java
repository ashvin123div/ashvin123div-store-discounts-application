package com.storediscounts.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * This class provides Authentication Parameter for Login.
 * 
 * @author ashvin
 *
 */
@Setter
@Getter
public class LoginRequest {

	@NotBlank(message = "Username can't be empty.")
	private String userName;

	@NotBlank(message = "Password can't be empty.")
	private String password;


	
}
