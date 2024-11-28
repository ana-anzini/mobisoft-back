package com.mobisoft.mobisoftapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mobisoft.mobisoftapi.dtos.delivery.DeliveryDTO;
import com.mobisoft.mobisoftapi.models.Deliveries;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.DeliveriesRepository;
import com.mobisoft.mobisoftapi.services.DeliveriesService;
import com.mobisoft.mobisoftapi.services.ProjectService;
import com.mobisoft.mobisoftapi.services.UserService;
import com.mobisoft.mobisoftapi.configs.exceptions.DeliveryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class DeliveriesServiceTest {

    @InjectMocks
    private DeliveriesService deliveriesService;

    @Mock
    private DeliveriesRepository deliveriesRepository;

    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @Mock
    private UserGroup userGroup;

    @Mock
    private Deliveries delivery;

    @Mock
    private DeliveryDTO deliveryDTO;

    @Mock
    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDelivery() {
        when(projectService.getProjectById(anyLong())).thenReturn(project);
        
        when(deliveriesRepository.save(any(Deliveries.class))).thenReturn(delivery);

        Deliveries result = deliveriesService.createDelivery(deliveryDTO);

        assertNotNull(result);
        verify(deliveriesRepository, times(1)).save(any(Deliveries.class));
        verify(projectService, times(1)).getProjectById(anyLong());
    }

    @Test
    void testGetAllDeliveries() {
        User user = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(user);
        when(user.getGroup()).thenReturn(userGroup);
        when(userGroup.getId()).thenReturn(1L);

        List<Deliveries> deliveries = Arrays.asList(delivery);
        when(deliveriesRepository.findByUserGroupId(anyLong())).thenReturn(deliveries);

        List<Deliveries> result = deliveriesService.getAllDeliveries();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(deliveriesRepository, times(1)).findByUserGroupId(anyLong());
    }

    @Test
    void testFindById() {
        Long id = 1L;
        when(deliveriesRepository.findById(id)).thenReturn(Optional.of(delivery));

        Deliveries result = deliveriesService.findById(id);

        assertNotNull(result);
        verify(deliveriesRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdThrowsException() {
        Long id = 1L;
        when(deliveriesRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(DeliveryNotFoundException.class, () -> deliveriesService.findById(id));
    }

    @Test
    void testFindByProjectId() {
        Long projectId = 1L;
        when(deliveriesRepository.findByProjectId(projectId)).thenReturn(delivery);

        Deliveries result = deliveriesService.findByProjectId(projectId);

        assertNotNull(result);
        verify(deliveriesRepository, times(1)).findByProjectId(projectId);
    }

    @Test
    void testUpdateDelivery() {
        Long projectId = 1L;
        when(deliveriesRepository.findByProjectId(projectId)).thenReturn(delivery);
        when(deliveriesRepository.save(any(Deliveries.class))).thenReturn(delivery);

        Deliveries result = deliveriesService.updateDelivery(projectId, deliveryDTO);

        assertNotNull(result);
        verify(deliveriesRepository, times(1)).save(any(Deliveries.class));
        verify(deliveriesRepository, times(1)).findByProjectId(projectId);
    }

    @Test
    void testDeleteDelivery() {
        Long id = 1L;
        when(deliveriesRepository.findById(id)).thenReturn(Optional.of(delivery));

        deliveriesService.deleteDelivery(id);

        verify(deliveriesRepository, times(1)).delete(delivery);
        verify(deliveriesRepository, times(1)).findById(id);
    }
}