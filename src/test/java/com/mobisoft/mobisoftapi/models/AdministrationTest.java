package com.mobisoft.mobisoftapi.models;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import com.mobisoft.mobisoftapi.repositories.AdministrationRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdministrationTest {

    @Mock
    private UserGroup userGroup;

    @InjectMocks
    private Administration administration;

    @Mock
    private AdministrationRepository administrationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        administration = new Administration();
    }

    @Test
    void testAdministrationCreation() {
        administration.setAdditionalSeller(new BigDecimal("100.50"));
        administration.setAdditionalProjectDesigner(new BigDecimal("150.75"));
        administration.setAdditionalFinancial(new BigDecimal("200.00"));
        administration.setAdditionalAssembler(new BigDecimal("120.25"));
        administration.setTax(new BigDecimal("50.00"));
        administration.setUserGroup(userGroup);
        
        assertNotNull(administration);
        assertEquals(new BigDecimal("100.50"), administration.getAdditionalSeller());
        assertEquals(new BigDecimal("150.75"), administration.getAdditionalProjectDesigner());
        assertEquals(new BigDecimal("200.00"), administration.getAdditionalFinancial());
        assertEquals(new BigDecimal("120.25"), administration.getAdditionalAssembler());
        assertEquals(new BigDecimal("50.00"), administration.getTax());
        assertEquals(userGroup, administration.getUserGroup());
    }

    @Test
    void testAdministrationWithNullValues() {
        administration.setAdditionalSeller(null);
        administration.setAdditionalProjectDesigner(null);
        administration.setAdditionalFinancial(null);
        administration.setAdditionalAssembler(null);
        administration.setTax(null);
        administration.setUserGroup(null);

        when(administrationRepository.save(administration))
            .thenThrow(new DataIntegrityViolationException("not-null property references a null or transient value"));

        assertThrows(DataIntegrityViolationException.class, () -> {
            administrationRepository.save(administration);
        });
    }
}