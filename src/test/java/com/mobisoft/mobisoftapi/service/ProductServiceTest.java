package com.mobisoft.mobisoftapi.service;

import com.mobisoft.mobisoftapi.dtos.products.ProductDTO;
import com.mobisoft.mobisoftapi.models.*;
import com.mobisoft.mobisoftapi.repositories.ProductRepository;
import com.mobisoft.mobisoftapi.services.CategoryService;
import com.mobisoft.mobisoftapi.services.ProductService;
import com.mobisoft.mobisoftapi.services.SupplierService;
import com.mobisoft.mobisoftapi.services.UserService;
import com.mobisoft.mobisoftapi.configs.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private SupplierService supplierService;
    
    @Mock
    private CategoryService categoryService;
    
    @Mock
    private UserService userService;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        product = new Product();
        product.setId(1L);
        product.setDescription("Product Description");
        product.setProductValue(BigDecimal.valueOf(100.00));

        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        product.setSupplier(supplier);

        UserGroup userGroup = new UserGroup();
        userGroup.setId(1L);
        product.setUserGroup(userGroup);

        productDTO = new ProductDTO();
        productDTO.setDescription("New Product Description");
        productDTO.setProductValue(BigDecimal.valueOf(200.00));
        productDTO.setCategoryId(1L);
        productDTO.setSupplierId(1L);
    }

    @Test
    public void createProduct_ShouldReturnSavedProduct() {
        when(supplierService.findById(anyLong())).thenReturn(new Supplier());
        when(categoryService.findById(anyLong())).thenReturn(new Category());
        when(userService.getLoggedUser()).thenReturn(new User());
        
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setDescription(productDTO.getDescription());
            return savedProduct;
        });

        Product createdProduct = productService.createProduct(productDTO);
        assertNotNull(createdProduct);
        assertEquals("New Product Description", createdProduct.getDescription());
        assertEquals(BigDecimal.valueOf(200.00), createdProduct.getProductValue());
        
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void getProductById_ShouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product foundProduct = productService.getProductById(1L);
        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getId());
        assertEquals("Product Description", foundProduct.getDescription());
    }

    @Test
    public void getProductById_ShouldThrowProductNotFoundException_WhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    public void updateProduct_ShouldReturnUpdatedProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(supplierService.findById(anyLong())).thenReturn(new Supplier());
        when(categoryService.findById(anyLong())).thenReturn(new Category());

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product updatedProduct = invocation.getArgument(0);
            updatedProduct.setDescription(productDTO.getDescription());
            return updatedProduct;
        });

        Product updatedProduct = productService.updateProduct(1L, productDTO);
        assertNotNull(updatedProduct);
        assertEquals("New Product Description", updatedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(200.00), updatedProduct.getProductValue());
    }

    @Test
    public void deleteProduct_ShouldDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.deleteProduct(1L);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void deleteProduct_ShouldThrowProductNotFoundException_WhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
    }
}