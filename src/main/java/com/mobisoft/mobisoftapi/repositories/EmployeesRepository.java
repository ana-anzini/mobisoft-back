package com.mobisoft.mobisoftapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.enums.employees.EmployeesType;
import com.mobisoft.mobisoftapi.models.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {
	
	List<Employees> findByEmployeesType(EmployeesType employeesType);
	
	List<Employees> findByUserGroupId(Long userGroupId);
}
