package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.products.ProductDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.models.Product;
import com.mobisoft.mobisoftapi.models.Supplier;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;
    private Supplier supplier;
    private Category category;
    private UserGroup userGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        supplier = new Supplier();
        supplier.setId(1L);

        category = new Category();
        category.setId(1L);

        userGroup = new UserGroup();
        userGroup.setId(1L);

        product = new Product();
        product.setId(1L);
        product.setDescription("Test Product");
        product.setSupplier(supplier);
        product.setCategory(category);
        product.setProductValue(BigDecimal.valueOf(100.00));
        product.setUserGroup(userGroup);

        productDTO = new ProductDTO();
        productDTO.setDescription("Test Product");
        productDTO.setSupplierId(1L);
        productDTO.setCategoryId(1L);
        productDTO.setProductValue(BigDecimal.valueOf(100.00));
        productDTO.setUserGroup(userGroup);
    }

    @Test
    void testCreateProduct() {
        when(productService.createProduct(productDTO)).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(productDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).createProduct(productDTO);
    }

    @Test
    void testGetAllProducts() {
        List<Product> productList = Arrays.asList(product);
        when(productService.getAllProducts()).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(productList, response.getBody());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetAllProductsByCategory() {
        List<Product> productList = Arrays.asList(product);
        when(productService.getProductsByCategory(1L)).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.getAllProductsByCategory(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(productList, response.getBody());
        verify(productService, times(1)).getProductsByCategory(1L);
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById(1L)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testUpdateProduct() {
        when(productService.updateProduct(1L, productDTO)).thenReturn(product);

        ResponseEntity<Product> response = productController.updateProduct(1L, productDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).updateProduct(1L, productDTO);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productService).deleteProduct(1L);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    void testDeleteProducts_Success() {
        doNothing().when(productService).deleteProducts(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = productController.deleteProducts(Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Produto(s) deletada(s) com sucesso.", response.getBody());
        verify(productService, times(1)).deleteProducts(Arrays.asList(1L, 2L));
    }

    @Test
    void testDeleteProducts_DataIntegrityViolation() {
        doThrow(new DataIntegrityViolationException("")).when(productService).deleteProducts(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = productController.deleteProducts(Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Não é possível excluir este produto, pois ele está em uso.", response.getBody());
        verify(productService, times(1)).deleteProducts(Arrays.asList(1L, 2L));
    }

    @Test
    void testDeleteProducts_GeneralException() {
        doThrow(new RuntimeException("General error")).when(productService).deleteProducts(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = productController.deleteProducts(Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Erro ao processar a solicitação.", response.getBody());
        verify(productService, times(1)).deleteProducts(Arrays.asList(1L, 2L));
    }
}