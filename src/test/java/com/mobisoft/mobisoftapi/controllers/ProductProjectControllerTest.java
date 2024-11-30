package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.productproject.ProductProjectDTO;
import com.mobisoft.mobisoftapi.dtos.productproject.ProductProjectDetailsDTO;
import com.mobisoft.mobisoftapi.models.Financial;
import com.mobisoft.mobisoftapi.models.Product;
import com.mobisoft.mobisoftapi.models.ProductProject;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.services.FinancialService;
import com.mobisoft.mobisoftapi.services.ProductProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductProjectControllerTest {

    @InjectMocks
    private ProductProjectController productProjectController;

    @Mock
    private ProductProjectService productProjectService;

    @Mock
    private FinancialService financialService;

    private ProductProject productProject;
    private ProductProjectDTO productProjectDTO;
    private Product product;
    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setId(1L);
        product.setDescription("Test Product");
        product.setProductValue(BigDecimal.valueOf(100.00));

        project = new Project();
        project.setId(1L);

        productProject = new ProductProject();
        productProject.setId(1L);
        productProject.setProject(project);
        productProject.setProduct(product);
        productProject.setProductValue(BigDecimal.valueOf(100.00));

        productProjectDTO = new ProductProjectDTO();
        productProjectDTO.setProjectId(1L);
        productProjectDTO.setProductId(1L);
        productProjectDTO.setProductValue(BigDecimal.valueOf(100.00));
    }

    @Test
    void testCreateProductProject() {
        when(productProjectService.createProductProject(productProjectDTO)).thenReturn(productProject);

        ResponseEntity<ProductProject> response = productProjectController.createProductProject(productProjectDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(productProject, response.getBody());
        verify(productProjectService, times(1)).createProductProject(productProjectDTO);
    }

    @Test
    void testUpdateProductProject() {
        when(productProjectService.updateProductProject(1L, productProjectDTO)).thenReturn(productProject);

        ResponseEntity<ProductProject> response = productProjectController.updateProductProject(1L, productProjectDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(productProject, response.getBody());
        verify(productProjectService, times(1)).updateProductProject(1L, productProjectDTO);
    }

    @Test
    void testDeleteProductProject() {
        doNothing().when(productProjectService).deleteProductProject(1L);

        ResponseEntity<Void> response = productProjectController.deleteProductProject(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(productProjectService, times(1)).deleteProductProject(1L);
    }

    @Test
    void testDeleteProductProjects() {
        doNothing().when(productProjectService).deleteProductProjects(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = productProjectController.deleteProductProjects(Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Produto(s) deletada(s) com sucesso.", response.getBody());
        verify(productProjectService, times(1)).deleteProductProjects(Arrays.asList(1L, 2L));
    }
    
    @Test
    void testGetAllProductsAndTotal() {
        List<ProductProject> productProjects = Arrays.asList(productProject);
        BigDecimal totalCosts = BigDecimal.valueOf(200.00);
        
        when(productProjectService.getProductsByProject(1L)).thenReturn(productProjects);
        
        Financial financialMock = mock(Financial.class);
        
        when(financialMock.getTotalValue()).thenReturn(totalCosts);
        
        when(financialService.findByProjectId(1L)).thenReturn(financialMock);

        ResponseEntity<ProductProjectDetailsDTO> response = productProjectController.getAllProductsAndTotal(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        assertEquals(totalCosts, response.getBody().getTotalValue());
        
        assertEquals(productProjects, response.getBody().getProducts());

        verify(productProjectService, times(1)).getProductsByProject(1L);
        verify(financialService, times(1)).findByProjectId(1L);
    }
}