package com.mobisoft.mobisoftapi.service;

import com.mobisoft.mobisoftapi.dtos.supplier.SupplierDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.models.Supplier;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.SupplierRepository;
import com.mobisoft.mobisoftapi.services.CategoryService;
import com.mobisoft.mobisoftapi.services.SupplierService;
import com.mobisoft.mobisoftapi.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SupplierService supplierService;

    @Mock
    private UserGroup userGroup;

    @Mock
    private Category category;

    @Mock
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        Supplier foundSupplier = supplierService.findById(1L);
        assertEquals(supplier, foundSupplier);
    }

    @Test
    void testCreateSupplier() {
        SupplierDTO supplierDTO = new SupplierDTO(
            null, 
            "Test", 
            "123456789", 
            "123456", 
            "test@example.com", 
            "12345", 
            "Test Address", 
            123, 
            "Test Neighborhood", 
            "Test Additional", 
            1L, 
            "Test Category"
        );
        
        User mockUser = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(mockUser);
        
        UserGroup mockUserGroup = mock(UserGroup.class);
        when(mockUser.getGroup()).thenReturn(mockUserGroup);
        when(mockUserGroup.getId()).thenReturn(1L);
        
        when(categoryService.findById(1L)).thenReturn(category);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier createdSupplier = supplierService.createSupplier(supplierDTO);
        assertNotNull(createdSupplier);
    }


    @Test
    void testGetAllSuppliers() {
        User mockUser = mock(User.class); // Criando o mock de User
        when(userService.getLoggedUser()).thenReturn(mockUser); // Mockando o retorno de getLoggedUser
        
        UserGroup mockUserGroup = mock(UserGroup.class); // Criando o mock de UserGroup
        when(mockUser.getGroup()).thenReturn(mockUserGroup); // Mockando o retorno de getGroup
        when(mockUserGroup.getId()).thenReturn(1L); // Mockando o ID do UserGroup
        
        when(supplierRepository.findByUserGroupId(1L)).thenReturn(List.of(supplier)); // Mockando a consulta ao repositório

        List<Supplier> suppliers = supplierService.getAllSuppliers(); // Chamando o método
        assertFalse(suppliers.isEmpty()); // Verificando se a lista de fornecedores não está vazia
    }

    @Test
    void testDeleteSuppliers() {
        when(supplierRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(supplier));
        supplierService.deleteSuppliers(List.of(1L, 2L));
        verify(supplierRepository, times(1)).deleteAll(anyList());
    }

    private UserGroup mockUserGroup() {
        UserGroup mockGroup = mock(UserGroup.class);
        when(mockGroup.getId()).thenReturn(1L);
        return mockGroup;
    }
}