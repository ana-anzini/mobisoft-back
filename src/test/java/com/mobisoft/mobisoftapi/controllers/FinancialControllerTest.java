package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.financial.FinancialDTO;
import com.mobisoft.mobisoftapi.models.Financial;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.services.FinancialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FinancialControllerTest {

    @InjectMocks
    private FinancialController financialController;

    @Mock
    private FinancialService financialService;

    private Financial financial;
    private FinancialDTO financialDTO;
    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project = new Project();
        project.setId(1L);

        Calendar firstPaymentDate = Calendar.getInstance();
        firstPaymentDate.set(2023, Calendar.JANUARY, 15);

        financial = new Financial();
        financial.setId(1L);
        financial.setInstallmentsNumber(12);
        financial.setFirstPayment(firstPaymentDate);
        financial.setDiscount(BigDecimal.valueOf(500.00));
        financial.setAdditionalExpenses(BigDecimal.valueOf(200.00));
        financial.setTotalValue(BigDecimal.valueOf(15000.00));
        financial.setTotalCusts(BigDecimal.valueOf(8000.00));
        financial.setTotalProfit(BigDecimal.valueOf(3000.00));
        financial.setTotalTax(BigDecimal.valueOf(1000.00));
        financial.setProject(project);

        financialDTO = new FinancialDTO();
        financialDTO.setInstallmentsNumber(12);
        financialDTO.setFirstPayment(firstPaymentDate);
        financialDTO.setDiscount(BigDecimal.valueOf(500.00));
        financialDTO.setAdditionalExpenses(BigDecimal.valueOf(200.00));
        financialDTO.setTotalValue(BigDecimal.valueOf(15000.00));
        financialDTO.setProjectId(1L);
    }

    @Test
    void testCreateFinancial() {
        when(financialService.createFinancial(financialDTO)).thenReturn(financial);

        ResponseEntity<Financial> response = financialController.createFinancial(financialDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(financial, response.getBody());
        verify(financialService, times(1)).createFinancial(financialDTO);
    }

    @Test
    void testGetFinancialById() {
        when(financialService.findById(1L)).thenReturn(financial);

        ResponseEntity<Financial> response = financialController.getFinancialById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(financial, response.getBody());
        verify(financialService, times(1)).findById(1L);
    }

    @Test
    void testGetFinancialByProjectId() {
        when(financialService.findByProjectId(1L)).thenReturn(financial);

        ResponseEntity<Financial> response = financialController.getFinancialByProjectId(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(financial, response.getBody());
        verify(financialService, times(1)).findByProjectId(1L);
    }

    @Test
    void testGetCalculatedValues() {
        when(financialService.calculateTotalProject(1L)).thenReturn(financial);

        ResponseEntity<Financial> response = financialController.getCalculatedValues(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(financial, response.getBody());
        verify(financialService, times(1)).calculateTotalProject(1L);
    }

    @Test
    void testUpdateFinancial() {
        when(financialService.updateFinancial(1L, financialDTO)).thenReturn(financial);

        ResponseEntity<Financial> response = financialController.updateFinancial(1L, financialDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(financial, response.getBody());
        verify(financialService, times(1)).updateFinancial(1L, financialDTO);
    }

    @Test
    void testDeleteFinancial() {
        doNothing().when(financialService).deleteFinancial(1L);

        ResponseEntity<Void> response = financialController.deleteFinancial(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(financialService, times(1)).deleteFinancial(1L);
    }
}