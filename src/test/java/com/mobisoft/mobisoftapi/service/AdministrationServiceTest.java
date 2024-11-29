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
    void testCreate() {
        AdministrationDTO administrationDTO = new AdministrationDTO();
        administrationDTO.setAdditionalSeller(new BigDecimal("2.0"));
        administrationDTO.setAdditionalProjectDesigner(new BigDecimal("3.0"));
        administrationDTO.setAdditionalFinancial(new BigDecimal("5.0"));
        administrationDTO.setAdditionalAssembler(new BigDecimal("1.0"));
        administrationDTO.setTax(new BigDecimal("10.0"));
        administrationDTO.setCompanyName("Test Company");
        administrationDTO.setSocialReason("Test Social Reason");
        administrationDTO.setAddress("123 Test St.");
        administrationDTO.setPhone("1234567890");
        administrationDTO.setEmail("test@company.com");

        User user = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(user);
        when(user.getGroup()).thenReturn(userGroup);

        Administration savedAdministration = new Administration();
        savedAdministration.setId(1L);
        savedAdministration.setAdditionalSeller(administrationDTO.getAdditionalSeller());
        savedAdministration.setAdditionalProjectDesigner(administrationDTO.getAdditionalProjectDesigner());
        savedAdministration.setAdditionalFinancial(administrationDTO.getAdditionalFinancial());
        savedAdministration.setAdditionalAssembler(administrationDTO.getAdditionalAssembler());
        savedAdministration.setTax(administrationDTO.getTax());
        savedAdministration.setUserGroup(userGroup);
        savedAdministration.setCompanyName(administrationDTO.getCompanyName());
        savedAdministration.setSocialReason(administrationDTO.getSocialReason());
        savedAdministration.setAddress(administrationDTO.getAddress());
        savedAdministration.setPhone(administrationDTO.getPhone());
        savedAdministration.setEmail(administrationDTO.getEmail());

        when(administrationRepository.save(any(Administration.class))).thenReturn(savedAdministration);

        Administration result = administrationService.create(administrationDTO);

        assertNotNull(result);
        assertEquals(administrationDTO.getCompanyName(), result.getCompanyName());
        assertEquals(administrationDTO.getSocialReason(), result.getSocialReason());
        assertEquals(administrationDTO.getPhone(), result.getPhone());
        assertEquals(administrationDTO.getEmail(), result.getEmail());

        verify(administrationRepository, times(1)).save(any(Administration.class));
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