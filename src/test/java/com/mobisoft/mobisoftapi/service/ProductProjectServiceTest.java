package com.mobisoft.mobisoftapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mobisoft.mobisoftapi.dtos.productproject.ProductProjectDTO;
import com.mobisoft.mobisoftapi.models.Financial;
import com.mobisoft.mobisoftapi.models.Product;
import com.mobisoft.mobisoftapi.models.ProductProject;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.repositories.ProductProjectRepository;
import com.mobisoft.mobisoftapi.services.FinancialService;
import com.mobisoft.mobisoftapi.services.ProductProjectService;
import com.mobisoft.mobisoftapi.services.ProductService;
import com.mobisoft.mobisoftapi.services.ProjectService;
import com.mobisoft.mobisoftapi.configs.exceptions.ProductProjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

class ProductProjectServiceTest {

    @InjectMocks
    private ProductProjectService productProjectService;

    @Mock
    private ProductProjectRepository productProjectRepository;

    @Mock
    private ProjectService projectService;

    @Mock
    private ProductService productService;

    @Mock
    private FinancialService financialService;

    @Mock
    private ProductProject productProject;

    @Mock
    private ProductProjectDTO productProjectDTO;

    @Mock
    private Project project;

    @Mock
    private Product product;

    @Mock
    private Financial financial;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProductProject() {
        when(productProjectDTO.getProjectId()).thenReturn(1L);
        when(productProjectDTO.getProductId()).thenReturn(1L);
        when(productProjectDTO.getProductValue()).thenReturn(BigDecimal.TEN);
        when(projectService.getProjectById(1L)).thenReturn(project);
        when(productService.getProductById(1L)).thenReturn(product);
        when(financialService.findByProjectId(1L)).thenReturn(financial);
        when(productProjectRepository.save(any(ProductProject.class))).thenReturn(productProject);

        ProductProject result = productProjectService.createProductProject(productProjectDTO);

        assertNotNull(result);
        verify(financialService, times(1)).save(any(Financial.class));
        verify(productProjectRepository, times(1)).save(any(ProductProject.class));
    }

    @Test
    void testCreateProductProjectWithNewFinancial() {
        when(productProjectDTO.getProjectId()).thenReturn(1L);
        when(productProjectDTO.getProductId()).thenReturn(1L);
        when(productProjectDTO.getProductValue()).thenReturn(BigDecimal.TEN);
        when(projectService.getProjectById(1L)).thenReturn(project);
        when(productService.getProductById(1L)).thenReturn(product);
        when(financialService.findByProjectId(1L)).thenReturn(null);

        when(productProjectRepository.save(any(ProductProject.class))).thenReturn(productProject);

        ProductProject result = productProjectService.createProductProject(productProjectDTO);

        assertNotNull(result);
        verify(financialService, times(1)).save(any(Financial.class));
        verify(productProjectRepository, times(1)).save(any(ProductProject.class));
    }

    @Test
    void testFindById() {
        Long id = 1L;
        when(productProjectRepository.findById(id)).thenReturn(Optional.of(productProject));

        ProductProject result = productProjectService.findById(id);

        assertNotNull(result);
        verify(productProjectRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdThrowsException() {
        Long id = 1L;
        when(productProjectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductProjectNotFoundException.class, () -> productProjectService.findById(id));
    }

    @Test
    void testUpdateProductProject() {
        Long id = 1L;
        when(productProjectDTO.getProjectId()).thenReturn(1L);
        when(productProjectDTO.getProductId()).thenReturn(1L);
        when(productProjectDTO.getProductValue()).thenReturn(BigDecimal.TEN);
        when(projectService.getProjectById(1L)).thenReturn(project);
        when(productService.getProductById(1L)).thenReturn(product);

        when(productProjectRepository.findById(id)).thenReturn(Optional.of(productProject));
        when(productProjectRepository.save(any(ProductProject.class))).thenReturn(productProject);

        ProductProject result = productProjectService.updateProductProject(id, productProjectDTO);

        assertNotNull(result);
        verify(productProjectRepository, times(1)).save(any(ProductProject.class));
    }

    @Test
    void testDeleteProductProject() {
        Long id = 1L;
        when(productProjectRepository.findById(id)).thenReturn(Optional.of(productProject));

        productProjectService.deleteProductProject(id);

        verify(productProjectRepository, times(1)).delete(productProject);
    }

    @Test
    void testDeleteProductProjectThrowsException() {
        Long id = 1L;
        when(productProjectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductProjectNotFoundException.class, () -> productProjectService.deleteProductProject(id));
    }

    @Test
    void testDeleteProductProjects() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<ProductProject> productProjects = Arrays.asList(productProject, productProject);
        when(productProjectRepository.findAllById(ids)).thenReturn(productProjects);

        productProjectService.deleteProductProjects(ids);

        verify(productProjectRepository, times(1)).deleteAll(productProjects);
    }

    @Test
    void testGetProductsByProject() {
        Long projectId = 1L;
        List<ProductProject> productProjects = Arrays.asList(productProject, productProject);
        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(productProjectRepository.findByProject(project)).thenReturn(productProjects);

        List<ProductProject> result = productProjectService.getProductsByProject(projectId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productProjectRepository, times(1)).findByProject(project);
    }
}
