package com.mobisoft.mobisoftapi.dtos.employees;

import java.util.Calendar;

import com.mobisoft.mobisoftapi.enums.employees.EmployeesType;

import lombok.Data;

@Data
public class EmployeesDTO {
	private String name;
    private String cpfOrCnpj;
    private String phone;
    private String email;
    private String cep;
    private String address;
    private int number;
    private String neighborhood;
    private String additional;
    private EmployeesType employeesType;
    private String rg;
    private String pis;
    private String ctps;
    private String salary;
    private Calendar admission;
    private Calendar dismissal;
}
