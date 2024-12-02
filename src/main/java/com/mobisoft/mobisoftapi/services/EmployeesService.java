package com.mobisoft.mobisoftapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.configs.exceptions.EmployeesNotFoundException;
import com.mobisoft.mobisoftapi.dtos.employees.EmployeesDTO;
import com.mobisoft.mobisoftapi.enums.employees.EmployeesType;
import com.mobisoft.mobisoftapi.models.Employees;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.EmployeesRepository;

@Service
public class EmployeesService {

	@Autowired
	private EmployeesRepository employeesRepository;
	
	@Autowired
	private UserService userService;

	public Employees createEmployee(EmployeesDTO employeesDTO) {
		UserGroup userGroup = userService.getLoggedUser().getGroup();
		Employees employee = new Employees();
		employee.setName(employeesDTO.getName());
		employee.setCpfOrCnpj(employeesDTO.getCpfOrCnpj());
		employee.setPhone(employeesDTO.getPhone());
		employee.setEmail(employeesDTO.getEmail());
		employee.setCep(employeesDTO.getCep());
		employee.setAddress(employeesDTO.getAddress());
		employee.setNumber(employeesDTO.getNumber());
		employee.setNeighborhood(employeesDTO.getNeighborhood());
		employee.setAdditional(employeesDTO.getAdditional());
		employee.setEmployeesType(employeesDTO.getEmployeesType());
		employee.setRg(employeesDTO.getRg());
		employee.setPis(employeesDTO.getPis());
		employee.setCtps(employeesDTO.getCtps());
		employee.setSalary(employeesDTO.getSalary());
		employee.setAdmission(employeesDTO.getAdmission());
		employee.setDismissal(employeesDTO.getDismissal());
		employee.setUserGroup(userGroup);

		return employeesRepository.save(employee);
	}

	public List<Employees> getAllEmployees() {
		UserGroup userGroup = userService.getLoggedUser().getGroup();
		return employeesRepository.findByUserGroupId(userGroup.getId());
	}

	public Employees findById(Long id) {
		return employeesRepository.findById(id).orElseThrow(() -> new EmployeesNotFoundException(id));
	}

	public Employees updateEmployee(Long id, EmployeesDTO employeesDTO) {
		Employees existingEmployee = findById(id);
		existingEmployee.setName(employeesDTO.getName());
		existingEmployee.setCpfOrCnpj(employeesDTO.getCpfOrCnpj());
		existingEmployee.setPhone(employeesDTO.getPhone());
		existingEmployee.setEmail(employeesDTO.getEmail());
		existingEmployee.setCep(employeesDTO.getCep());
		existingEmployee.setAddress(employeesDTO.getAddress());
		existingEmployee.setNumber(employeesDTO.getNumber());
		existingEmployee.setNeighborhood(employeesDTO.getNeighborhood());
		existingEmployee.setAdditional(employeesDTO.getAdditional());
		existingEmployee.setEmployeesType(employeesDTO.getEmployeesType());
		existingEmployee.setRg(employeesDTO.getRg());
		existingEmployee.setPis(employeesDTO.getPis());
		existingEmployee.setCtps(employeesDTO.getCtps());
		existingEmployee.setSalary(employeesDTO.getSalary());
		existingEmployee.setAdmission(employeesDTO.getAdmission());
		existingEmployee.setDismissal(employeesDTO.getDismissal());

		return employeesRepository.save(existingEmployee);
	}

	public void deleteEmployee(Long id) {
		Employees employee = findById(id);
		employeesRepository.delete(employee);
	}

	public void deleteEmployees(List<Long> ids) {
		List<Employees> employees = employeesRepository.findAllById(ids);
		employeesRepository.deleteAll(employees);
	}

	public List<Employees> findByEmployeesType(EmployeesType employeesType) {
		return employeesRepository.findByEmployeesType(employeesType);
	}
}
