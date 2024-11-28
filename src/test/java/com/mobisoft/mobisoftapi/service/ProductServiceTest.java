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

        // Criando e mockando os serviços necessários
        product = new Product();
        product.setId(1L);
        product.setDescription("Original Product");
        product.setProductValue(new BigDecimal("100.00"));

        supplier = new Supplier();  // Mockando um supplier
        category = new Category();  // Mockando uma categoria
        when(supplierService.findById(1L)).thenReturn(supplier);
        when(categoryService.findById(1L)).thenReturn(category);

        // Mockando o comportamento do repositório
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);  // Assegura que o produto não será null ao salvar
    }

    @Test
    void testCreateProduct() {
        // Mockando o comportamento do getLoggedUser para retornar um usuário com um grupo
        User loggedUser = new User();
        when(userService.getLoggedUser()).thenReturn(loggedUser);  // Mockando a resposta do getLoggedUser()

        // Mockando os dados do DTO
        when(productDTO.getDescription()).thenReturn("New Product");
        when(productDTO.getProductValue()).thenReturn(new BigDecimal("200.00"));
        when(productDTO.getSupplierId()).thenReturn(1L);
        when(productDTO.getCategoryId()).thenReturn(1L);

        // Mockando as dependências
        when(supplierService.findById(1L)).thenReturn(supplier);  // Garantir que supplier não é null
        when(categoryService.findById(1L)).thenReturn(category);  // Garantir que category não é null

        // Verificando se o produto foi salvo após a criação
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetProductById() {
        // Mockando o comportamento do repositório
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Chamando o método a ser testado
        Product foundProduct = productService.getProductById(1L);

        // Verificando os resultados
        assertNotNull(foundProduct);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        // Mockando o comportamento do repositório para não encontrar o produto
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Chamando o método a ser testado e verificando se lança exceção
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateProduct() {
        // Mockando o comportamento do repositório para retornar um produto existente
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Mockando os dados do DTO
        when(productDTO.getDescription()).thenReturn("Updated Product");
        when(productDTO.getProductValue()).thenReturn(new BigDecimal("150.00"));
        when(productDTO.getSupplierId()).thenReturn(1L);
        when(productDTO.getCategoryId()).thenReturn(1L);

        // Mockando as dependências
        when(supplierService.findById(1L)).thenReturn(supplier); // Garantir que supplier não é null
        when(categoryService.findById(1L)).thenReturn(category); // Garantir que category não é null

        // Chamando o método a ser testado
        Product updatedProduct = productService.updateProduct(1L, productDTO);

        // Verificando os resultados
        assertNotNull(updatedProduct); // Verificando se o produto não é null após a atualização
        assertEquals("Updated Product", updatedProduct.getDescription()); // Verificando a descrição
        assertEquals(new BigDecimal("150.00"), updatedProduct.getProductValue()); // Verificando o valor do produto

        // Verificando se o produto foi salvo após a atualização
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void testDeleteProduct() {
        // Mockando o comportamento do repositório
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Chamando o método a ser testado
        productService.deleteProduct(1L);

        // Verificando os resultados
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Mockando o comportamento do repositório para não encontrar o produto
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Chamando o método a ser testado e verificando se lança exceção
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository, never()).delete(any(Product.class));
    }
}
