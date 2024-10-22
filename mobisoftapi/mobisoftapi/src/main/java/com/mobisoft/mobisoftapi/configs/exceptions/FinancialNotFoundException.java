package com.mobisoft.mobisoftapi.configs.exceptions;

public class FinancialNotFoundException extends RuntimeException {

	public FinancialNotFoundException(Long id) {
        super("Financeiro não encontrado com o id: " + id);
    }
}
