package com.mobisoft.mobisoftapi.dtos;

import com.mobisoft.mobisoftapi.dtos.administration.AdministrationDTO;
import com.mobisoft.mobisoftapi.models.UserGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class AdministrationDTOTest {

    private AdministrationDTO administrationDTO;
    private UserGroup userGroup;

    @BeforeEach
    void setUp() {
        userGroup = new UserGroup();
        administrationDTO = new AdministrationDTO();
    }

    @Test
    void testAdministrationDTOCreation() {
        administrationDTO.setAdditionalSeller(new BigDecimal("100.50"));
        administrationDTO.setAdditionalProjectDesigner(new BigDecimal("150.75"));
        administrationDTO.setAdditionalFinancial(new BigDecimal("200.00"));
        administrationDTO.setAdditionalAssembler(new BigDecimal("120.25"));
        administrationDTO.setTax(new BigDecimal("50.00"));
        administrationDTO.setUserGroup(userGroup);

        assertNotNull(administrationDTO);
        assertEquals(new BigDecimal("100.50"), administrationDTO.getAdditionalSeller());
        assertEquals(new BigDecimal("150.75"), administrationDTO.getAdditionalProjectDesigner());
        assertEquals(new BigDecimal("200.00"), administrationDTO.getAdditionalFinancial());
        assertEquals(new BigDecimal("120.25"), administrationDTO.getAdditionalAssembler());
        assertEquals(new BigDecimal("50.00"), administrationDTO.getTax());
        assertEquals(userGroup, administrationDTO.getUserGroup());
    }

    @Test
    void testAdministrationDTOWithoutUserGroup() {
        administrationDTO.setUserGroup(null);

        assertNull(administrationDTO.getUserGroup());
    }

    @Test
    void testAdministrationDTOWithPartialValues() {
        administrationDTO.setAdditionalSeller(new BigDecimal("100.50"));
        administrationDTO.setTax(new BigDecimal("25.00"));

        assertNotNull(administrationDTO.getAdditionalSeller());
        assertEquals(new BigDecimal("100.50"), administrationDTO.getAdditionalSeller());
        assertNotNull(administrationDTO.getTax());
        assertEquals(new BigDecimal("25.00"), administrationDTO.getTax());

        assertNull(administrationDTO.getAdditionalProjectDesigner());
        assertNull(administrationDTO.getAdditionalFinancial());
        assertNull(administrationDTO.getAdditionalAssembler());
    }
}
