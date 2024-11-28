package com.mobisoft.mobisoftapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mobisoft.mobisoftapi.dtos.administration.AdministrationDTO;
import com.mobisoft.mobisoftapi.models.Administration;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.AdministrationRepository;
import com.mobisoft.mobisoftapi.services.AdministrationService;
import com.mobisoft.mobisoftapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

class AdministrationServiceTest {

    @InjectMocks
    private AdministrationService administrationService;

    @Mock
    private AdministrationRepository administrationRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserGroup userGroup;

    @Mock
    private AdministrationDTO administrationDTO;

    @Mock
    private Administration administration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Long id = 1L;
        when(administrationRepository.findById(id)).thenReturn(Optional.of(administration));

        Administration result = administrationService.findById(id);

        assertNotNull(result);
        verify(administrationRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdThrowsExceptionWhenNotFound() {
        Long id = 1L;
        when(administrationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> administrationService.findById(id));
    }

    @Test
    void testFindByUserGroup() {
        User user = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(user);
        
        when(user.getGroup()).thenReturn(userGroup);

        when(administrationRepository.findByUserGroup(userGroup)).thenReturn(administration);

        Administration result = administrationService.findByUserGroup();

        assertNotNull(result);
        verify(administrationRepository, times(1)).findByUserGroup(userGroup);
        verify(userService, times(1)).getLoggedUser();
    }


    @Test
    void testCreate() {
        User user = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(user);
        
        when(user.getGroup()).thenReturn(userGroup);

        when(administrationRepository.save(any(Administration.class))).thenReturn(administration);

        Administration result = administrationService.create(administrationDTO);

        assertNotNull(result);
        verify(administrationRepository, times(1)).save(any(Administration.class));
        verify(userService, times(1)).getLoggedUser();
    }

    private UserGroup mockUserGroup() {
        UserGroup group = new UserGroup();
        return group;
    }
}