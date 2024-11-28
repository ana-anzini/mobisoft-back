package com.mobisoft.mobisoftapi.service;

import com.mobisoft.mobisoftapi.configs.security.SecurityFilter;
import com.mobisoft.mobisoftapi.repositories.UserRepository;
import com.mobisoft.mobisoftapi.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SecurityFilterTest {

    @InjectMocks
    private SecurityFilter securityFilter;

    @Mock
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetails userDetails;

    private String validToken = "validToken";
    private String userLogin = "user1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoFilterInternal_WithValidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        when(tokenService.validateToken(validToken)).thenReturn(userLogin);
        when(userRepository.findByLogin(userLogin)).thenReturn(userDetails);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(tokenService, times(1)).validateToken(validToken);
        verify(userRepository, times(1)).findByLogin(userLogin);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithNoToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(tokenService, times(0)).validateToken(validToken);
        verify(userRepository, times(0)).findByLogin(userLogin);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithInvalidToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer " + validToken);
        when(tokenService.validateToken(validToken)).thenReturn(null);

        securityFilter.doFilterInternal(request, response, filterChain);

        verify(tokenService, times(1)).validateToken(validToken);
        verify(userRepository, times(0)).findByLogin(userLogin);
        verify(filterChain, times(1)).doFilter(request, response);
    }
}