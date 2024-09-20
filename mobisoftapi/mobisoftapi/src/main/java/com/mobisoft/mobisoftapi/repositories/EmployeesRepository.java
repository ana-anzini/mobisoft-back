package com.mobisoft.mobisoftapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobisoft.mobisoftapi.models.Employees;

public interface EmployeesRepository extends JpaRepository<Employees, Long> {

}
