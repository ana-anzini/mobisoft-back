package com.mobisoft.mobisoftapi.models;

import java.util.Calendar;

import com.mobisoft.mobisoftapi.base.Base;
import com.mobisoft.mobisoftapi.enums.employees.EmployeesType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "EMPLOYEES")
public class Employees extends Base {

    @Column(name="employees_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EmployeesType employeesType;
    
    @Column(name="rg", nullable = false)
    private String rg;
    
    @Column(name="pis", nullable = false)
    private String pis;
    
    @Column(name="ctps", nullable = false)
    private String ctps;
    
    @Column(name="salary", nullable = false)
    private String salary;
    
    @Column(name="admission", nullable = true)
    private Calendar admission;
    
    @Column(name="dismissal", nullable = true)
    private Calendar dismissal;
}
