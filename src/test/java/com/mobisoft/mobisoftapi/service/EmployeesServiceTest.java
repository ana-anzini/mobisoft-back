package com.mobisoft.mobisoftapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mobisoft.mobisoftapi.dtos.employees.EmployeesDTO;
import com.mobisoft.mobisoftapi.enums.employees.EmployeesType;
import com.mobisoft.mobisoftapi.models.Employees;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.EmployeesRepository;
import com.mobisoft.mobisoftapi.services.EmployeesService;
import com.mobisoft.mobisoftapi.services.UserService;
import com.mobisoft.mobisoftapi.configs.exceptions.EmployeesNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class EmployeesServiceTest {

    @InjectMocks
    private EmployeesService employeesService;

    @Mock
    private EmployeesRepository employeesRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserGroup userGroup;

    @Mock
    private Employees employee;

    @Mock
    private EmployeesDTO employeesDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee() {
        User user = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(user);
        when(user.getGroup()).thenReturn(userGroup);

        when(employeesRepository.save(any(Employees.class))).thenReturn(employee);

        Employees result = employeesService.createEmployee(employeesDTO);

        assertNotNull(result);
        verify(employeesRepository, times(1)).save(any(Employees.class));
        verify(userService, times(1)).getLoggedUser();
    }

    @Test
    void testGetAllEmployees() {
        User user = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(user);
        when(user.getGroup()).thenReturn(userGroup);
        when(userGroup.getId()).thenReturn(1L);

        List<Employees> employees = Arrays.asList(employee);
        when(employeesRepository.findByUserGroupId(anyLong())).thenReturn(employees);

        List<Employees> result = employeesService.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeesRepository, times(1)).findByUserGroupId(anyLong());
    }

    @Test
    void testFindById() {
        Long id = 1L;
        when(employeesRepository.findById(id)).thenReturn(Optional.of(employee));

        Employees result = employeesService.findById(id);

        assertNotNull(result);
        verify(employeesRepository, times(1)).findById(id);
    }

    @Test
    void testFindByIdThrowsException() {
        Long id = 1L;
        when(employeesRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EmployeesNotFoundException.class, () -> employeesService.findById(id));
    }

    @Test
    void testUpdateEmployee() {
        Long id = 1L;
        when(employeesRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeesRepository.save(any(Employees.class))).thenReturn(employee);

        Employees result = employeesService.updateEmployee(id, employeesDTO);

        assertNotNull(result);
        verify(employeesRepository, times(1)).save(any(Employees.class));
        verify(employeesRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteEmployee() {
        Long id = 1L;
        when(employeesRepository.findById(id)).thenReturn(Optional.of(employee));

        employeesService.deleteEmployee(id);

        verify(employeesRepository, times(1)).delete(employee);
        verify(employeesRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteEmployees() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Employees> employeesList = Arrays.asList(employee, employee);
        when(employeesRepository.findAllById(ids)).thenReturn(employeesList);

        employeesService.deleteEmployees(ids);

        verify(employeesRepository, times(1)).deleteAll(employeesList);
        verify(employeesRepository, times(1)).findAllById(ids);
    }

    @Test
    void testFindByEmployeesType() {
        EmployeesType employeesType = EmployeesType.ASSEMBLER;
        List<Employees> employeesList = Arrays.asList(employee, employee);
        when(employeesRepository.findByEmployeesType(employeesType)).thenReturn(employeesList);

        List<Employees> result = employeesService.findByEmployeesType(employeesType);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeesRepository, times(1)).findByEmployeesType(employeesType);
    }
}