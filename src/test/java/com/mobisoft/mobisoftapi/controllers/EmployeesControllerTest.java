package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.employees.EmployeesDTO;
import com.mobisoft.mobisoftapi.enums.employees.EmployeesType;
import com.mobisoft.mobisoftapi.enums.user.UserRole;
import com.mobisoft.mobisoftapi.models.Employees;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.services.EmployeesService;
import com.mobisoft.mobisoftapi.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeesControllerTest {

    @InjectMocks
    private EmployeesController employeesController;

    @Mock
    private EmployeesService employeesService;
    
    @Mock
    private UserService userService;

    private Employees employee;
    private EmployeesDTO employeesDTO;
    private User mockUser;
    private UserGroup mockGroup;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Calendar admissionDate = Calendar.getInstance();
        admissionDate.set(2020, Calendar.JANUARY, 1);

        employee = new Employees();
        employee.setId(1L);
        employee.setEmployeesType(EmployeesType.ASSEMBLER);
        employee.setRg("123456789");
        employee.setPis("987654321");
        employee.setCtps("CTPS12345");
        employee.setSalary("5000");
        employee.setAdmission(admissionDate);

        employeesDTO = new EmployeesDTO();
        employeesDTO.setEmployeesType(EmployeesType.ASSEMBLER);
        employeesDTO.setRg("123456789");
        employeesDTO.setPis("987654321");
        employeesDTO.setCtps("CTPS12345");
        employeesDTO.setSalary("5000");
        employeesDTO.setAdmission(admissionDate);
        
        mockUser = mock(User.class);
        mockGroup = mock(UserGroup.class);

        when(userService.getLoggedUser()).thenReturn(mockUser);
        when(mockUser.getGroup()).thenReturn(mockGroup);
        when(mockGroup.getId()).thenReturn(1L);
    }

    @Test
    void testCreateEmployee() {
        when(employeesService.createEmployee(employeesDTO)).thenReturn(employee);

        ResponseEntity<Employees> response = employeesController.createEmployee(employeesDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(employee, response.getBody());
        verify(employeesService, times(1)).createEmployee(employeesDTO);
    }

    @Test
    void testGetAllEmployees() {
        List<Employees> employeesList = Arrays.asList(employee);
        when(employeesService.getAllEmployees()).thenReturn(employeesList);

        ResponseEntity<List<Employees>> response = employeesController.getAllEmployees();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(employeesList, response.getBody());
        verify(employeesService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() {
        when(employeesService.findById(1L)).thenReturn(employee);

        ResponseEntity<Employees> response = employeesController.getEmployeeById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(employee, response.getBody());
        verify(employeesService, times(1)).findById(1L);
    }

    @Test
    void testUpdateEmployee() {
        when(employeesService.updateEmployee(1L, employeesDTO)).thenReturn(employee);

        ResponseEntity<Employees> response = employeesController.updateEmployee(1L, employeesDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(employee, response.getBody());
        verify(employeesService, times(1)).updateEmployee(1L, employeesDTO);
    }
    
    @Test
    void testDeleteEmployees() {
        List<Long> ids = Arrays.asList(1L, 2L);

        doNothing().when(employeesService).deleteEmployees(ids);

        ResponseEntity<String> response = employeesController.deleteEmployees(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Funcionário(s) deletada(s) com sucesso.", response.getBody());
        verify(employeesService, times(1)).deleteEmployees(ids);
    }
    
    @Test
    void testFindByType() {
        EmployeesType type = EmployeesType.ASSEMBLER;
        List<Employees> mockEmployeesList = List.of(employee);

        when(employeesService.findByEmployeesTypeAndUserGroupId(type, mockGroup.getId()))
                .thenReturn(mockEmployeesList);

        ResponseEntity<List<Employees>> response = employeesController.findByType(type);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockEmployeesList, response.getBody());

        verify(userService, times(1)).getLoggedUser();
        verify(employeesService, times(1)).findByEmployeesTypeAndUserGroupId(type, mockGroup.getId());
    }
}