package com.storediscounts.service;

import com.storediscounts.entity.User;
import com.storediscounts.payload.LoginRequest;
import com.storediscounts.payload.LoginResponse;

public interface UserService {

	User save(User user);

	LoginResponse loginUser(LoginRequest loginRequest);

}
