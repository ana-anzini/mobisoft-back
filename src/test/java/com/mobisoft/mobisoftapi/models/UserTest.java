package com.mobisoft.mobisoftapi.models;

import com.mobisoft.mobisoftapi.enums.user.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @Mock
    private UserGroup userGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("testLogin", "testPassword", "Test User", UserRole.USER, userGroup);
    }

    @Test
    void testGetUsername() {
        assertEquals("testLogin", user.getUsername());
    }

    @Test
    void testGetAuthorities_UserRole() {
        user = new User("testLogin", "testPassword", "Test User", UserRole.USER, userGroup);
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testGetAuthorities_AdminRole() {
        user = new User("adminLogin", "adminPassword", "Admin User", UserRole.ADMIN, userGroup);
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
        assertTrue(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(user.isEnabled());
    }
}