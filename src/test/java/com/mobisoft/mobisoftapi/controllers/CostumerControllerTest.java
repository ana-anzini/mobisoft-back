package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.costumers.CostumerDTO;
import com.mobisoft.mobisoftapi.models.Costumer;
import com.mobisoft.mobisoftapi.services.CostumerService;
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

class CostumerControllerTest {

    @InjectMocks
    private CostumerController costumerController;

    @Mock
    private CostumerService costumerService;

    private Costumer costumer;
    private CostumerDTO costumerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        costumer = new Costumer();
        costumer.setId(1L);
        costumer.setName("John Doe");
        costumer.setEmail("johndoe@example.com");

        costumerDTO = new CostumerDTO();
        costumerDTO.setName("John Doe");
        costumerDTO.setEmail("johndoe@example.com");
    }

    @Test
    void testCreateCostumer() {
        when(costumerService.createCostumer(costumerDTO)).thenReturn(costumer);

        ResponseEntity<Costumer> response = costumerController.createCostumer(costumerDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(costumer, response.getBody());
        verify(costumerService, times(1)).createCostumer(costumerDTO);
    }

    @Test
    void testGetCostumerById() {
        when(costumerService.getCostumerById(1L)).thenReturn(costumer);

        ResponseEntity<Costumer> response = costumerController.getCostumerById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(costumer, response.getBody());
        verify(costumerService, times(1)).getCostumerById(1L);
    }

    @Test
    void testGetAllCostumers() {
        List<Costumer> costumers = Arrays.asList(costumer);
        when(costumerService.getAllCostumers()).thenReturn(costumers);

        ResponseEntity<List<Costumer>> response = costumerController.getAllCostumers();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(costumers, response.getBody());
        verify(costumerService, times(1)).getAllCostumers();
    }

    @Test
    void testUpdateCostumer() {
        when(costumerService.updateCostumer(1L, costumerDTO)).thenReturn(costumer);

        ResponseEntity<Costumer> response = costumerController.updateCostumer(1L, costumerDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(costumer, response.getBody());
        verify(costumerService, times(1)).updateCostumer(1L, costumerDTO);
    }

    @Test
    void testDeleteCostumer_Success() {
        List<Long> ids = Arrays.asList(1L, 2L);

        doNothing().when(costumerService).deleteCostumers(ids);

        ResponseEntity<String> response = costumerController.deleteCostumer(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente(s) deletada(s) com sucesso.", response.getBody());
        verify(costumerService, times(1)).deleteCostumers(ids);
    }
}