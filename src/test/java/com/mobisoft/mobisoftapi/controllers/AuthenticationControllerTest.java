package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.auth.AuthenticationDTO;
import com.mobisoft.mobisoftapi.dtos.auth.LoginResponseDTO;
import com.mobisoft.mobisoftapi.dtos.auth.RegisterDTO;
import com.mobisoft.mobisoftapi.enums.user.UserRole;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.repositories.UserRepository;
import com.mobisoft.mobisoftapi.services.TokenService;
import com.mobisoft.mobisoftapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserService userService;

    private AuthenticationDTO authenticationDTO;
    private RegisterDTO registerDTO;
    private User mockUser;
    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up mock data
        authenticationDTO = new AuthenticationDTO("userLogin", "userPassword");
        mockUser = new User("userLogin", "encodedPassword", "User Name", UserRole.ADMIN, null);
        token = "mockJwtToken";
    }

    @Test
    void testRegister_UserAlreadyExists() {
        RegisterDTO registerDTO = new RegisterDTO("userLogin", "userPassword", "User Name", UserRole.ADMIN);

        when(userRepository.findByLogin(registerDTO.login())).thenReturn(mockUser);

        ResponseEntity response = authenticationController.register(registerDTO);

        assertEquals(400, response.getStatusCode().value());
        verify(userRepository, times(1)).findByLogin(registerDTO.login());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testGetInfo() {
        when(userService.getLoggedUser()).thenReturn(mockUser);

        ResponseEntity<User> response = authenticationController.getInfoDetails();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockUser, response.getBody());
        verify(userService, times(1)).getLoggedUser();
    }

    @Test
    void testIsAuthenticated_Authenticated() {
        when(userService.getLoggedUser()).thenReturn(mockUser);

        ResponseEntity<Boolean> response = authenticationController.checkAuthentication();

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody());
        verify(userService, times(1)).getLoggedUser();
    }

    @Test
    void testIsAuthenticated_NotAuthenticated() {
        when(userService.getLoggedUser()).thenThrow(new RuntimeException("User not found"));

        ResponseEntity<Boolean> response = authenticationController.checkAuthentication();

        assertEquals(200, response.getStatusCode().value());
        assertFalse(response.getBody());
        verify(userService, times(1)).getLoggedUser();
    }
}