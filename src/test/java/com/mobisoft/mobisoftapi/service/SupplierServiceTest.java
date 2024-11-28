package com.mobisoft.mobisoftapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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

import java.util.Optional;
import java.util.List;
import java.util.NoSuchElementException;

class SupplierServiceTest {

    @InjectMocks
    private SupplierService supplierService;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private UserService userService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private UserGroup userGroup;

    @Mock
    private Supplier supplier;

    @Mock
    private SupplierDTO supplierDTO;

    @Mock
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        Supplier result = supplierService.findById(1L);

        assertNotNull(result);
        assertEquals(supplier, result);

        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> supplierService.findById(1L));

        verify(supplierRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateSupplier_Success() {
        when(userService.getLoggedUser()).thenReturn(mock(User.class));
        when(userService.getLoggedUser().getGroup()).thenReturn(userGroup);
        when(categoryService.findById(supplierDTO.getCategoryId())).thenReturn(category);

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier result = supplierService.createSupplier(supplierDTO);

        assertNotNull(result);
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void testCreateSupplier_NoCategory() {
        when(userService.getLoggedUser()).thenReturn(mock(User.class));
        when(userService.getLoggedUser().getGroup()).thenReturn(userGroup);
        when(categoryService.findById(supplierDTO.getCategoryId())).thenReturn(null);

        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier result = supplierService.createSupplier(supplierDTO);

        assertNotNull(result);
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void testGetAllSuppliers_Success() {
        when(userService.getLoggedUser()).thenReturn(mock(User.class));
        when(userService.getLoggedUser().getGroup()).thenReturn(userGroup);
        when(supplierRepository.findByUserGroupId(userGroup.getId())).thenReturn(List.of(supplier));

        List<Supplier> result = supplierService.getAllSuppliers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(supplierRepository, times(1)).findByUserGroupId(userGroup.getId());
    }

    @Test
    void testUpdateSupplier_Success() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(categoryService.findById(supplierDTO.getCategoryId())).thenReturn(category);
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier result = supplierService.updateSupplier(1L, supplierDTO);

        assertNotNull(result);
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void testUpdateSupplier_NotFound() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> supplierService.updateSupplier(1L, supplierDTO));

        verify(supplierRepository, never()).save(any(Supplier.class));
    }

    @Test
    void testDeleteSuppliers_Success() {
        when(supplierRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(supplier));

        supplierService.deleteSuppliers(List.of(1L, 2L));

        verify(supplierRepository, times(1)).deleteAll(anyList());
    }
}