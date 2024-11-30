package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.delivery.DeliveryDTO;
import com.mobisoft.mobisoftapi.models.Deliveries;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.services.DeliveriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryControllerTest {

    @InjectMocks
    private DeliveryController deliveryController;

    @Mock
    private DeliveriesService deliveriesService;

    private Deliveries delivery;
    private DeliveryDTO deliveryDTO;
    private Project project;
    private UserGroup userGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        project = new Project();
        project.setId(1L);

        userGroup = new UserGroup();
        userGroup.setId(1L);

        Calendar deliveryDate = Calendar.getInstance();
        deliveryDate.set(2023, Calendar.DECEMBER, 15);

        delivery = new Deliveries();
        delivery.setId(1L);
        delivery.setAddressClient(true);
        delivery.setCep("12345678");
        delivery.setAddress("123 Test Street");
        delivery.setNumber(100);
        delivery.setNeighborhood("Test Neighborhood");
        delivery.setAdditional("Test Additional");
        delivery.setDeliveryDate(deliveryDate);
        delivery.setFreight(BigDecimal.valueOf(50.00));
        delivery.setProject(project);
        delivery.setUserGroup(userGroup);

        deliveryDTO = new DeliveryDTO();
        deliveryDTO.setAddressClient(true);
        deliveryDTO.setCep("12345678");
        deliveryDTO.setAddress("123 Test Street");
        deliveryDTO.setNumber(100);
        deliveryDTO.setNeighborhood("Test Neighborhood");
        deliveryDTO.setAdditional("Test Additional");
        deliveryDTO.setDeliveryDate(deliveryDate);
        deliveryDTO.setFreight(BigDecimal.valueOf(50.00));
        deliveryDTO.setProjectId(1L);
        deliveryDTO.setUserGroup(userGroup);
    }

    @Test
    void testCreateDelivery() {
        when(deliveriesService.createDelivery(deliveryDTO)).thenReturn(delivery);

        ResponseEntity<Deliveries> response = deliveryController.createDelivery(deliveryDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(delivery, response.getBody());
        verify(deliveriesService, times(1)).createDelivery(deliveryDTO);
    }

    @Test
    void testGetAllDeliveries() {
        List<Deliveries> deliveries = Arrays.asList(delivery);
        when(deliveriesService.getAllDeliveries()).thenReturn(deliveries);

        ResponseEntity<List<Deliveries>> response = deliveryController.getAllDeliveries();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(deliveries, response.getBody());
        verify(deliveriesService, times(1)).getAllDeliveries();
    }

    @Test
    void testGetDeliveryById() {
        when(deliveriesService.findById(1L)).thenReturn(delivery);

        ResponseEntity<Deliveries> response = deliveryController.getDeliveryById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(delivery, response.getBody());
        verify(deliveriesService, times(1)).findById(1L);
    }

    @Test
    void testGetDeliveryByProjectId() {
        when(deliveriesService.findByProjectId(1L)).thenReturn(delivery);

        ResponseEntity<Deliveries> response = deliveryController.getDelivery(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(delivery, response.getBody());
        verify(deliveriesService, times(1)).findByProjectId(1L);
    }

    @Test
    void testUpdateDelivery() {
        when(deliveriesService.updateDelivery(1L, deliveryDTO)).thenReturn(delivery);

        ResponseEntity<Deliveries> response = deliveryController.updateDelivery(1L, deliveryDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(delivery, response.getBody());
        verify(deliveriesService, times(1)).updateDelivery(1L, deliveryDTO);
    }

    @Test
    void testDeleteDelivery() {
        doNothing().when(deliveriesService).deleteDeliveries(anyList());

        ResponseEntity<Void> response = deliveryController.deleteDelivery(Arrays.asList(1L));

        assertEquals(204, response.getStatusCode().value());

        verify(deliveriesService, times(1)).deleteDeliveries(Arrays.asList(1L));
    }
}