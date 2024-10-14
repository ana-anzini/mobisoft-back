package com.mobisoft.mobisoftapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobisoft.mobisoftapi.models.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {

}
