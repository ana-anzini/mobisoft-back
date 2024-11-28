package com.mobisoft.mobisoftapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mobisoft.mobisoftapi.dtos.costumers.CostumerDTO;
import com.mobisoft.mobisoftapi.models.Costumer;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.CostumerRepository;
import com.mobisoft.mobisoftapi.services.CostumerService;
import com.mobisoft.mobisoftapi.services.UserService;
import com.mobisoft.mobisoftapi.configs.exceptions.CostumerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class CostumerServiceTest {

    @InjectMocks
    private CostumerService costumerService;

    @Mock
    private CostumerRepository costumerRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserGroup userGroup;

    @Mock
    private Costumer costumer;

    @Mock
    private CostumerDTO costumerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCostumer() {
        User user = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(user);
        when(user.getGroup()).thenReturn(userGroup);
        
        when(costumerRepository.save(any(Costumer.class))).thenReturn(costumer);

        Costumer result = costumerService.createCostumer(costumerDTO);

        assertNotNull(result);
        verify(costumerRepository, times(1)).save(any(Costumer.class));
        verify(userService, times(1)).getLoggedUser();
    }

    @Test
    void testGetCostumerById() {
        Long id = 1L;
        when(costumerRepository.findById(id)).thenReturn(Optional.of(costumer));

        Costumer result = costumerService.getCostumerById(id);

        assertNotNull(result);
        verify(costumerRepository, times(1)).findById(id);
    }

    @Test
    void testGetCostumerByIdThrowsException() {
        Long id = 1L;
        when(costumerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CostumerNotFoundException.class, () -> costumerService.getCostumerById(id));
    }

    @Test
    void testGetAllCostumers() {
        User user = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(user);
        
        UserGroup userGroup = mock(UserGroup.class);
        when(user.getGroup()).thenReturn(userGroup);
        when(userGroup.getId()).thenReturn(1L);

        List<Costumer> costumers = Arrays.asList(costumer);
        when(costumerRepository.findByUserGroupId(anyLong())).thenReturn(costumers);

        List<Costumer> result = costumerService.getAllCostumers();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(costumerRepository, times(1)).findByUserGroupId(anyLong());
        verify(userService, times(1)).getLoggedUser();
    }


    @Test
    void testUpdateCostumer() {
        Long id = 1L;
        when(costumerRepository.findById(id)).thenReturn(Optional.of(costumer));
        when(costumerRepository.save(any(Costumer.class))).thenReturn(costumer);

        Costumer result = costumerService.updateCostumer(id, costumerDTO);

        assertNotNull(result);
        verify(costumerRepository, times(1)).save(any(Costumer.class));
        verify(costumerRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteCostumers() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Costumer> costumers = Arrays.asList(costumer, costumer);
        when(costumerRepository.findAllById(ids)).thenReturn(costumers);

        costumerService.deleteCostumers(ids);

        verify(costumerRepository, times(1)).deleteAll(costumers);
    }
}