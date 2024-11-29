package com.mobisoft.mobisoftapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mobisoft.mobisoftapi.dtos.financial.FinancialDTO;
import com.mobisoft.mobisoftapi.enums.payment.PaymentType;
import com.mobisoft.mobisoftapi.models.Administration;
import com.mobisoft.mobisoftapi.models.Deliveries;
import com.mobisoft.mobisoftapi.models.Financial;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.repositories.FinancialRepository;
import com.mobisoft.mobisoftapi.services.FinancialService;
import com.mobisoft.mobisoftapi.services.ProjectService;
import com.mobisoft.mobisoftapi.services.AdministrationService;
import com.mobisoft.mobisoftapi.services.DeliveriesService;
import com.mobisoft.mobisoftapi.configs.exceptions.FinancialNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;

class FinancialServiceTest {

    @InjectMocks
    private FinancialService financialService;

    @Mock
    private FinancialRepository financialRepository;

    @Mock
    private ProjectService projectService;

    @Mock
    private AdministrationService administrationService;

    @Mock
    private DeliveriesService deliveriesService;

    @Mock
    private Financial financial;

    @Mock
    private FinancialDTO financialDTO;

    @Mock
    private Administration administration;

    @Mock
    private Deliveries deliveries;

    @Mock
    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(administration.getTax()).thenReturn(new BigDecimal("10.0"));
        when(administration.getAdditionalFinancial()).thenReturn(new BigDecimal("5.0"));
        when(administration.getAdditionalProjectDesigner()).thenReturn(new BigDecimal("3.0"));
        when(administration.getAdditionalSeller()).thenReturn(new BigDecimal("2.0"));
        when(administration.getAdditionalAssembler()).thenReturn(new BigDecimal("1.0"));
    }

    @Test
    void testCreateFinancial() {
        when(financialRepository.save(any(Financial.class))).thenReturn(financial);

        when(financialDTO.getInstallmentsNumber()).thenReturn(5);
        when(financialDTO.getFirstPayment()).thenReturn(Calendar.getInstance());
        when(financialDTO.getPaymentType()).thenReturn(PaymentType.DEBIT);
        when(financialDTO.getDiscount()).thenReturn(new BigDecimal("100.00"));
        when(financialDTO.getAdditionalExpenses()).thenReturn(new BigDecimal("50.00"));
        when(financialDTO.getTotalValue()).thenReturn(new BigDecimal("5000.00"));
        when(financialDTO.getTotalCusts()).thenReturn(new BigDecimal("3000.00"));
        when(financialDTO.getTotalProjectDesigner()).thenReturn(new BigDecimal("200.00"));
        when(financialDTO.getTotalSeller()).thenReturn(new BigDecimal("150.00"));
        when(financialDTO.getTotalProfit()).thenReturn(new BigDecimal("1000.00"));

        Financial result = financialService.createFinancial(financialDTO);

        assertNotNull(result);
        verify(financialRepository, times(1)).save(any(Financial.class));
    }

    @Test
    void testFindById() {
        Long financialId = 1L;
        when(financialRepository.findById(financialId)).thenReturn(Optional.of(financial));

        Financial result = financialService.findById(financialId);

        assertNotNull(result);
        verify(financialRepository, times(1)).findById(financialId);
    }

    @Test
    void testFindByIdNotFound() {
        Long financialId = 1L;
        when(financialRepository.findById(financialId)).thenReturn(Optional.empty());

        assertThrows(FinancialNotFoundException.class, () -> financialService.findById(financialId));
    }

    @Test
    void testFindByProjectId() {
        Long projectId = 1L;
        when(financialRepository.findByProjectId(projectId)).thenReturn(financial);

        Financial result = financialService.findByProjectId(projectId);

        assertNotNull(result);
        verify(financialRepository, times(1)).findByProjectId(projectId);
    }

    @Test
    void testUpdateFinancial() {
        Long projectId = 1L;
        when(financialRepository.findByProjectId(projectId)).thenReturn(financial);
        when(financialRepository.save(any(Financial.class))).thenReturn(financial);

        when(financialDTO.getInstallmentsNumber()).thenReturn(10);
        when(financialDTO.getFirstPayment()).thenReturn(Calendar.getInstance());
        when(financialDTO.getPaymentType()).thenReturn(PaymentType.DEBIT);
        when(financialDTO.getDiscount()).thenReturn(new BigDecimal("200.00"));
        when(financialDTO.getAdditionalExpenses()).thenReturn(new BigDecimal("100.00"));

        Financial result = financialService.updateFinancial(projectId, financialDTO);

        assertNotNull(result);
        verify(financialRepository, times(1)).save(any(Financial.class));
    }

    @Test
    void testDeleteFinancial() {
        Long financialId = 1L;
        when(financialRepository.findById(financialId)).thenReturn(Optional.of(financial));

        financialService.deleteFinancial(financialId);

        verify(financialRepository, times(1)).delete(any(Financial.class));
    }
    
    @Test
    void testCalculateTotalProject() {
        Long projectId = 1L;

        when(financialRepository.findByProjectId(projectId)).thenReturn(financial);
        when(administrationService.findByUserGroup()).thenReturn(administration);
        when(deliveriesService.findByProjectId(projectId)).thenReturn(deliveries);

        when(administration.getTax()).thenReturn(new BigDecimal("10.0"));
        when(administration.getAdditionalFinancial()).thenReturn(new BigDecimal("5.0"));
        when(administration.getAdditionalProjectDesigner()).thenReturn(new BigDecimal("3.0"));
        when(administration.getAdditionalSeller()).thenReturn(new BigDecimal("2.0"));
        when(administration.getAdditionalAssembler()).thenReturn(new BigDecimal("1.0"));

        when(deliveries.getFreight()).thenReturn(new BigDecimal("100.0"));

        when(financial.getTotalCusts()).thenReturn(new BigDecimal("3000.0"));

        Financial result = financialService.calculateTotalProject(projectId);

        assertNotNull(result);
        verify(financialRepository, times(1)).findByProjectId(projectId);
        verify(administrationService, times(1)).findByUserGroup();
        verify(deliveriesService, times(1)).findByProjectId(projectId);

        when(financialRepository.findByProjectId(projectId)).thenReturn(null);
        assertThrows(FinancialNotFoundException.class, () -> financialService.calculateTotalProject(projectId));

        when(administrationService.findByUserGroup()).thenReturn(null);
        assertThrows(FinancialNotFoundException.class, () -> financialService.calculateTotalProject(projectId));

        when(deliveriesService.findByProjectId(projectId)).thenReturn(null);
        assertThrows(FinancialNotFoundException.class, () -> financialService.calculateTotalProject(projectId));
    }
    
    @Test
    void testSave() {
        when(financialRepository.save(financial)).thenReturn(financial);

        financialService.save(financial);

        verify(financialRepository, times(1)).save(financial);
    }

    @Test
    void testDeleteFinancialNotFound() {
        Long financialId = 1L;
        when(financialRepository.findById(financialId)).thenReturn(Optional.empty());

        assertThrows(FinancialNotFoundException.class, () -> financialService.deleteFinancial(financialId));
    }
}