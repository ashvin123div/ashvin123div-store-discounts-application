package com.storediscounts.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.storediscounts.entity.User;
import com.storediscounts.exception.ValidationException;
import com.storediscounts.payload.LoginRequest;
import com.storediscounts.payload.LoginResponse;
import com.storediscounts.repository.UserRepository;
import com.storediscounts.service.UserService;
import com.storediscounts.serviceimpl.UserServiceImpl;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void testSaveUser_Success() {
        // Create a sample user
        User user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setPassword("testPassword");
        // Mock the behavior of the userRepository
        when(userRepository.findUserByUserName(user.getUserName())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        // Call the save method in the user service
        User savedUser = userService.save(user);

        // Verify that the userRepository's save method was called with the user
        verify(userRepository).save(user);

        // Assert that the returned user is the same as the one returned by the repository
        assertEquals(user, savedUser);
    }

    @Test
    public void testSaveUser_UserAlreadyExist() {
        // Create a sample user
        User user = new User();
        user.setId(1L);
        user.setUserName("existingUser");
        user.setPassword("testPassword");
        // Mock the behavior of the userRepository
        when(userRepository.findUserByUserName(user.getUserName())).thenReturn(user);

        // Call the save method in the user service and expect a ValidationException
        assertThrows(ValidationException.class, () -> userService.save(user));

        // Verify that the userRepository's save method was not called
        verify(userRepository).findUserByUserName(user.getUserName());
        verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    public void testLoginUser_Success() {
        // Create a sample login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("testUser");
        loginRequest.setPassword("testPassword");
        // Create a sample user
        User user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setPassword("testPassword");
        when(userRepository.findUserByUserName(loginRequest.getUserName())).thenReturn(user);

        LoginResponse loginResponse = userService.loginUser(loginRequest);
        verify(userRepository).findUserByUserName(loginRequest.getUserName());
        assertNotNull(loginResponse);
        assertEquals(user.getUserName(), loginResponse.getUserName());
        assertEquals(user.getId(), loginResponse.getId());
    }

    @Test
    public void testLoginUser_UserNotFound() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("nonExistingUser");
        loginRequest.setPassword("testPassword");
        // Mock the behavior of the userRepository
        when(userRepository.findUserByUserName(loginRequest.getUserName())).thenReturn(null);
        assertThrows(ValidationException.class, () -> userService.loginUser(loginRequest));


    }
}
