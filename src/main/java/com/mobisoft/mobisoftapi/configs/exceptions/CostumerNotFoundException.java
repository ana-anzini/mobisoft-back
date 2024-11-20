package com.mobisoft.mobisoftapi.configs.exceptions;

public class CostumerNotFoundException extends RuntimeException {

	public CostumerNotFoundException(Long id) {
        super("Cliente não encontrado com o id: " + id);
    }
}
