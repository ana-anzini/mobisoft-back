package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.administration.AdministrationDTO;
import com.mobisoft.mobisoftapi.models.Administration;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.services.AdministrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdministrationControllerTest {

    @InjectMocks
    private AdministrationController administrationController;

    @Mock
    private AdministrationService administrationService;

    private Administration administration;
    private AdministrationDTO administrationDTO;
    private UserGroup userGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userGroup = new UserGroup();
        userGroup.setId(1L);

        administration = new Administration();
        administration.setId(1L);
        administration.setAdditionalSeller(BigDecimal.valueOf(100.00));
        administration.setAdditionalProjectDesigner(BigDecimal.valueOf(200.00));
        administration.setAdditionalFinancial(BigDecimal.valueOf(300.00));
        administration.setAdditionalAssembler(BigDecimal.valueOf(400.00));
        administration.setTax(BigDecimal.valueOf(50.00));
        administration.setUserGroup(userGroup);

        administrationDTO = new AdministrationDTO();
        administrationDTO.setAdditionalSeller(BigDecimal.valueOf(100.00));
        administrationDTO.setAdditionalProjectDesigner(BigDecimal.valueOf(200.00));
        administrationDTO.setAdditionalFinancial(BigDecimal.valueOf(300.00));
        administrationDTO.setAdditionalAssembler(BigDecimal.valueOf(400.00));
        administrationDTO.setTax(BigDecimal.valueOf(50.00));
        administrationDTO.setUserGroup(userGroup);
    }

    @Test
    void testCreateAdministration() {
        when(administrationService.create(administrationDTO)).thenReturn(administration);

        ResponseEntity<Administration> response = administrationController.createAdministration(administrationDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(administration, response.getBody());
        verify(administrationService, times(1)).create(administrationDTO);
    }

    @Test
    void testUpdateAdministration() {
        Optional<Administration> updatedAdministration = Optional.of(administration);
        when(administrationService.update(administrationDTO)).thenReturn(updatedAdministration);

        ResponseEntity<Optional<Administration>> response = administrationController.updateAdministration(administrationDTO);

        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isPresent());
        assertEquals(administration, response.getBody().get());
        verify(administrationService, times(1)).update(administrationDTO);
    }

    @Test
    void testFindAdministration() {
        when(administrationService.findByUserGroup()).thenReturn(administration);

        ResponseEntity<Administration> response = administrationController.findAdministration();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(administration, response.getBody());
        verify(administrationService, times(1)).findByUserGroup();
    }
}
