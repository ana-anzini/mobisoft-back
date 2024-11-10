package com.mobisoft.mobisoftapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobisoft.mobisoftapi.dtos.employees.EmployeesDTO;
import com.mobisoft.mobisoftapi.models.Employees;
import com.mobisoft.mobisoftapi.services.EmployeesService;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @PostMapping
    public ResponseEntity<Employees> createEmployee(@RequestBody EmployeesDTO employeesDTO) {
        Employees newEmployee = employeesService.createEmployee(employeesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Employees>> getAllEmployees() {
        List<Employees> employees = employeesService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employees> getEmployeeById(@PathVariable Long id) {
        Employees employee = employeesService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employees> updateEmployee(@PathVariable Long id, @RequestBody EmployeesDTO employeesDTO) {
        Employees updatedEmployee = employeesService.updateEmployee(id, employeesDTO);
        return ResponseEntity.ok(updatedEmployee);
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteEmployees(@RequestParam List<Long> ids) {
        try {
            employeesService.deleteEmployees(ids);
            return ResponseEntity.ok("Produto(s) deletada(s) com sucesso.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.ok("Não é possível excluir este produto, pois ele está em uso.");
        } catch (Exception e) {
            return ResponseEntity.ok("Erro ao processar a solicitação.");
        }
    }
}
