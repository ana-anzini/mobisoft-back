package com.mobisoft.mobisoftapi.service;

import com.mobisoft.mobisoftapi.configs.exceptions.ProductNotFoundException;
import com.mobisoft.mobisoftapi.dtos.products.ProductDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.models.Product;
import com.mobisoft.mobisoftapi.models.Supplier;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.ProductRepository;
import com.mobisoft.mobisoftapi.services.CategoryService;
import com.mobisoft.mobisoftapi.services.ProductService;
import com.mobisoft.mobisoftapi.services.SupplierService;
import com.mobisoft.mobisoftapi.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SupplierService supplierService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private UserService userService;

    @Mock
    private Product product;

    @Mock
    private ProductDTO productDTO;

    @Mock
    private Supplier supplier;

    @Mock
    private Category category;

    @Mock
    private UserGroup userGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User mockUser = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(mockUser);

        when(mockUser.getGroup()).thenReturn(userGroup);

        product = new Product();
        product.setId(1L);
        product.setDescription("Original Product");
        product.setProductValue(new BigDecimal("100.00"));

        when(supplierService.findById(1L)).thenReturn(supplier);
        when(categoryService.findById(1L)).thenReturn(category);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        when(productDTO.getDescription()).thenReturn("Updated Product");
        when(productDTO.getProductValue()).thenReturn(new BigDecimal("150.00"));
        when(productDTO.getSupplierId()).thenReturn(1L);
        when(productDTO.getCategoryId()).thenReturn(1L);

        when(supplierService.findById(1L)).thenReturn(supplier);
        when(categoryService.findById(1L)).thenReturn(category);

        Product updatedProduct = productService.updateProduct(1L, productDTO);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getDescription());
        assertEquals(new BigDecimal("150.00"), updatedProduct.getProductValue());

        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, never()).delete(any(Product.class));
    }
    
    @Test
    void testCreateProduct() {
        when(userService.getLoggedUser().getGroup()).thenReturn(userGroup);

        when(productDTO.getDescription()).thenReturn("New Product");
        when(productDTO.getProductValue()).thenReturn(new BigDecimal("200.00"));
        when(productDTO.getSupplierId()).thenReturn(1L);
        when(productDTO.getCategoryId()).thenReturn(1L);

        when(supplierService.findById(1L)).thenReturn(supplier);
        when(categoryService.findById(1L)).thenReturn(category);

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product newProduct = productService.createProduct(productDTO);

        assertNotNull(newProduct);
        assertEquals("New Product", newProduct.getDescription());
        assertEquals(new BigDecimal("200.00"), newProduct.getProductValue());
        assertEquals(supplier, newProduct.getSupplier());
        assertEquals(category, newProduct.getCategory());
        assertEquals(userGroup, newProduct.getUserGroup());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        when(userService.getLoggedUser().getGroup()).thenReturn(userGroup);
        when(productRepository.findByUserGroupId(userGroup.getId())).thenReturn(List.of(product));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findByUserGroupId(userGroup.getId());
    }

    @Test
    void testGetProductsByCategory() {
        Long categoryId = 1L;
        when(categoryService.findById(categoryId)).thenReturn(category);
        when(productRepository.findByCategory(category)).thenReturn(List.of(product));

        List<Product> products = productService.getProductsByCategory(categoryId);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
        verify(productRepository, times(1)).findByCategory(category);
    }

    @Test
    void testDeleteProducts() {
        List<Long> productIds = List.of(1L, 2L);
        when(productRepository.findAllById(productIds)).thenReturn(List.of(product));

        productService.deleteProducts(productIds);

        verify(productRepository, times(1)).deleteAll(anyList());
    }

    @Test
    void testImportProductsFromCSV_ValidFile() throws Exception {
        String csvContent = "categoryCode,supplierCode,description,value\n" +
                            "CAT001,SUP001,Product A,100.00\n" +
                            "CAT001,SUP001,Product B,200.00";

        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(csvContent.getBytes()));

        when(userService.getLoggedUser().getGroup()).thenReturn(userGroup);
        when(categoryService.findByCode("CAT001")).thenReturn(category);
        when(supplierService.findByCode("SUP001")).thenReturn(supplier);

        productService.importProductsFromCSV(file);

        verify(productRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testImportProductsFromCSV_InvalidFile() throws Exception {
        String invalidCsvContent = "categoryCode,supplierCode,description,value\n" +
                                   "CAT001,SUP001,Product A";

        MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(new ByteArrayInputStream(invalidCsvContent.getBytes()));

        when(userService.getLoggedUser().getGroup()).thenReturn(userGroup);

        assertThrows(IllegalArgumentException.class, () -> productService.importProductsFromCSV(file));
        verify(productRepository, never()).saveAll(anyList());
    }
}
