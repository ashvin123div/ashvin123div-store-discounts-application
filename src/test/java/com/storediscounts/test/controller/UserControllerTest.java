package com.storediscounts.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.storediscounts.controller.UserController;
import com.storediscounts.entity.User;
import com.storediscounts.payload.LoginRequest;
import com.storediscounts.payload.LoginResponse;
import com.storediscounts.service.UserService;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    public void testSave_ValidUser() {
        // Create a sample user
        User user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setPassword("testPassword");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.save(user)).thenReturn(user);

        // Call the save method in the user controller
        ResponseEntity<?> responseEntity = userController.save(user, bindingResult);
        verify(userService).save(user);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

   

    @Test
    public void testLogin_ValidLoginRequest() {
        // Create a sample login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("testUser");
        loginRequest.setPassword("testPassword");
        when(bindingResult.hasErrors()).thenReturn(false);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(1L);
        loginResponse.setUserName("testUser");
        when(userService.loginUser(loginRequest)).thenReturn(loginResponse);
        ResponseEntity<?> responseEntity = userController.login(loginRequest, bindingResult);
        verify(userService).loginUser(loginRequest);
        assertNotNull(responseEntity);
    }
}
