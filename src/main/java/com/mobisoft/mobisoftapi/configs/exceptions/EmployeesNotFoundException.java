package com.mobisoft.mobisoftapi.configs.exceptions;

public class EmployeesNotFoundException extends RuntimeException {

	public EmployeesNotFoundException(Long id) {
        super("Funcionário não encontrado com o id: " + id);
    }
}
