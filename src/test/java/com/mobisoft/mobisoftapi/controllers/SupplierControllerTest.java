package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.supplier.SupplierDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.models.Supplier;
import com.mobisoft.mobisoftapi.services.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierControllerTest {

    @InjectMocks
    private SupplierController supplierController;

    @Mock
    private SupplierService supplierService;

    private Supplier supplier;
    private SupplierDTO supplierDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Category category = new Category();
        category.setId(1L);
        
        supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Fornecedor");
        supplier.setCpfOrCnpj("134.344.434-14");
        supplier.setPhone("47 988818-3420");
        supplier.setEmail("anabeanzini@gmail.com");
        supplier.setCep("89333-333");
        supplier.setAddress("Rua");
        supplier.setNumber(107);
        supplier.setNeighborhood("Floresta");
        supplier.setCategory(category);
        supplierDTO = new SupplierDTO();
        supplierDTO.setId(1L);
        supplierDTO.setName("Fornecedor");
        supplierDTO.setCpfOrCnpj("134.344.434-14");
        supplierDTO.setPhone("47 988818-3420");
        supplierDTO.setEmail("anabeanzini@gmail.com");
        supplierDTO.setCep("89333-333");
        supplierDTO.setAddress("Rua");
        supplierDTO.setNumber(107);
        supplierDTO.setNeighborhood("Floresta");
    }

    @Test
    void testCreateSupplier() {
        when(supplierService.createSupplier(supplierDTO)).thenReturn(supplier);

        ResponseEntity<Supplier> response = supplierController.createSupplier(supplierDTO);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertEquals(supplier, response.getBody());
        verify(supplierService, times(1)).createSupplier(supplierDTO);
    }

    @Test
    void testGetSupplierById() {
        when(supplierService.findById(1L)).thenReturn(supplier);

        ResponseEntity<Supplier> response = supplierController.getSupplierById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(supplier, response.getBody());
        verify(supplierService, times(1)).findById(1L);
    }

    @Test
    void testGetAllSuppliers() {
        List<Supplier> suppliers = Arrays.asList(supplier);
        when(supplierService.getAllSuppliers()).thenReturn(suppliers);

        ResponseEntity<List<Supplier>> response = supplierController.getAllSuppliers();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(suppliers, response.getBody());
        verify(supplierService, times(1)).getAllSuppliers();
    }

    @Test
    void testUpdateSupplier() {
        when(supplierService.updateSupplier(1L, supplierDTO)).thenReturn(supplier);

        ResponseEntity<Supplier> response = supplierController.updateSupplier(1L, supplierDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(supplier, response.getBody());
        verify(supplierService, times(1)).updateSupplier(1L, supplierDTO);
    }

    @Test
    void testDeleteSuppliers() {
        doNothing().when(supplierService).deleteSuppliers(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = supplierController.deleteSuppliers(Arrays.asList(1L, 2L));

        assertEquals(204, response.getStatusCode().value());
        verify(supplierService, times(1)).deleteSuppliers(Arrays.asList(1L, 2L));
    }
}