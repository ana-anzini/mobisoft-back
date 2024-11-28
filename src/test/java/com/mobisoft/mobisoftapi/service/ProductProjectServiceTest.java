package com.mobisoft.mobisoftapi.service;

import com.mobisoft.mobisoftapi.configs.exceptions.ProductProjectNotFoundException;
import com.mobisoft.mobisoftapi.dtos.productproject.ProductProjectDTO;
import com.mobisoft.mobisoftapi.models.Financial;
import com.mobisoft.mobisoftapi.models.Product;
import com.mobisoft.mobisoftapi.models.ProductProject;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.repositories.ProductProjectRepository;
import com.mobisoft.mobisoftapi.services.ProductProjectService;
import com.mobisoft.mobisoftapi.services.ProductService;
import com.mobisoft.mobisoftapi.services.FinancialService;
import com.mobisoft.mobisoftapi.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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

    private ProductProjectDTO productProjectDTO;
    private ProductProject productProject;
    private Product product;
    private Project project;
    private Financial financial;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project = new Project();
        project.setId(1L);

        product = new Product();
        product.setId(1L);

        financial = new Financial();
        financial.setTotalCusts(new BigDecimal("100.00"));

        productProjectDTO = new ProductProjectDTO();
        productProjectDTO.setProductId(1L);
        productProjectDTO.setProjectId(1L);
        productProjectDTO.setProductValue(new BigDecimal("200.00"));

        productProject = new ProductProject();
        productProject.setId(1L);
        productProject.setProduct(product);
        productProject.setProject(project);
        productProject.setProductValue(new BigDecimal("200.00"));
    }

    @Test
    void testCreateProductProject_Success() {
        when(projectService.getProjectById(anyLong())).thenReturn(project);
        when(productService.getProductById(anyLong())).thenReturn(product);
        when(financialService.findByProjectId(anyLong())).thenReturn(null);
        when(productProjectRepository.save(any(ProductProject.class))).thenReturn(productProject);

        ProductProject result = productProjectService.createProductProject(productProjectDTO);

        assertNotNull(result);
        assertEquals(productProjectDTO.getProductValue(), result.getProductValue());
        verify(productProjectRepository, times(1)).save(any(ProductProject.class));
        verify(financialService, times(1)).save(any(Financial.class));
    }

    @Test
    void testCreateProductProject_ExistingFinancial_Success() {
        when(projectService.getProjectById(anyLong())).thenReturn(project);
        when(productService.getProductById(anyLong())).thenReturn(product);
        when(financialService.findByProjectId(anyLong())).thenReturn(financial);
        when(productProjectRepository.save(any(ProductProject.class))).thenReturn(productProject);

        ProductProject result = productProjectService.createProductProject(productProjectDTO);

        assertNotNull(result);
        assertEquals(productProjectDTO.getProductValue(), result.getProductValue());
        verify(productProjectRepository, times(1)).save(any(ProductProject.class));
        verify(financialService, times(1)).save(any(Financial.class));
        assertEquals(new BigDecimal("300.00"), financial.getTotalCusts());
    }

    @Test
    void testFindById_Success() {
        when(productProjectRepository.findById(anyLong())).thenReturn(Optional.of(productProject));

        ProductProject result = productProjectService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productProjectRepository, times(1)).findById(anyLong());
    }

    @Test
    void testFindById_NotFound() {
        when(productProjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductProjectNotFoundException.class, () -> productProjectService.findById(1L));

        verify(productProjectRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateProductProject_Success() {
        ProductProjectDTO updateDTO = new ProductProjectDTO();
        updateDTO.setProductId(1L);
        updateDTO.setProjectId(1L);
        updateDTO.setProductValue(new BigDecimal("300.00"));

        when(projectService.getProjectById(anyLong())).thenReturn(project);
        when(productService.getProductById(anyLong())).thenReturn(product);
        when(productProjectRepository.findById(anyLong())).thenReturn(Optional.of(productProject));
        when(productProjectRepository.save(any(ProductProject.class))).thenReturn(productProject);

        ProductProject result = productProjectService.updateProductProject(1L, updateDTO);

        assertNotNull(result);
        assertEquals(updateDTO.getProductValue(), result.getProductValue());
        verify(productProjectRepository, times(1)).save(any(ProductProject.class));
    }

    @Test
    void testDeleteProductProject_Success() {
        when(productProjectRepository.findById(anyLong())).thenReturn(Optional.of(productProject));

        productProjectService.deleteProductProject(1L);

        verify(productProjectRepository, times(1)).delete(any(ProductProject.class));
    }

    @Test
    void testDeleteProductProject_NotFound() {
        when(productProjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ProductProjectNotFoundException.class, () -> productProjectService.deleteProductProject(1L));

        verify(productProjectRepository, never()).delete(any(ProductProject.class));
    }

    @Test
    void testDeleteProductProjects_Success() {
        when(productProjectRepository.findAllById(anyList())).thenReturn(List.of(productProject));

        productProjectService.deleteProductProjects(List.of(1L));

        verify(productProjectRepository, times(1)).deleteAll(anyList());
    }

    @Test
    void testGetProductsByProject_Success() {
        when(projectService.getProjectById(anyLong())).thenReturn(project);
        when(productProjectRepository.findByProject(any(Project.class))).thenReturn(List.of(productProject));

        List<ProductProject> result = productProjectService.getProductsByProject(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(productProjectRepository, times(1)).findByProject(any(Project.class));
    }
}