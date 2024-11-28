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

import java.math.BigDecimal;
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
    private Administration administration;

    @Mock
    private AdministrationDTO administrationDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        when(administrationRepository.findById(1L)).thenReturn(Optional.of(administration));

        Administration result = administrationService.findById(1L);

        assertNotNull(result);
        assertEquals(administration, result);

        verify(administrationRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(administrationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> administrationService.findById(1L));

        verify(administrationRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByUserGroup_Success() {
        User user = mock(User.class);

        when(userService.getLoggedUser()).thenReturn(user);
        when(user.getGroup()).thenReturn(userGroup);

        when(administrationRepository.findByUserGroup(userGroup)).thenReturn(administration);

        Administration result = administrationService.findByUserGroup();

        assertNotNull(result);

        verify(administrationRepository, times(1)).findByUserGroup(userGroup);
    }

    @Test
    void testUpdate_Success() {
        // Mock do User e UserGroup
        User mockUser = mock(User.class);
        UserGroup mockUserGroup = mock(UserGroup.class);
        when(mockUser.getGroup()).thenReturn(mockUserGroup);
        when(userService.getLoggedUser()).thenReturn(mockUser);

        Administration existingAdministration = new Administration();
        existingAdministration.setId(1L);
        existingAdministration.setUserGroup(mockUserGroup);

        when(administrationRepository.findByUserGroup(mockUserGroup)).thenReturn(existingAdministration);

        AdministrationDTO administrationDTO = mock(AdministrationDTO.class);
        when(administrationDTO.getAdditionalSeller()).thenReturn(new BigDecimal("100.50"));
        when(administrationDTO.getAdditionalProjectDesigner()).thenReturn(new BigDecimal("150.75"));
        when(administrationDTO.getAdditionalFinancial()).thenReturn(new BigDecimal("200.00"));
        when(administrationDTO.getAdditionalAssembler()).thenReturn(new BigDecimal("120.25"));
        when(administrationDTO.getTax()).thenReturn(new BigDecimal("50.00"));

        when(administrationRepository.save(existingAdministration)).thenReturn(existingAdministration);

        Optional<Administration> result = administrationService.update(administrationDTO);

        assertTrue(result.isPresent());
        assertEquals(existingAdministration, result.get());

        verify(administrationRepository, times(1)).save(existingAdministration);
    }


    @Test
    void testUpdate_NotFound() {
        User mockUser = mock(User.class);
        UserGroup mockUserGroup = mock(UserGroup.class);
        when(mockUser.getGroup()).thenReturn(mockUserGroup);
        when(userService.getLoggedUser()).thenReturn(mockUser);

        when(administrationService.findByUserGroup()).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> administrationService.update(administrationDTO));

        verify(administrationRepository, never()).save(any(Administration.class));
    }
}