package com.mobisoft.mobisoftapi.configs.exceptions;

public class CategoryNotFoundException extends RuntimeException {

	public CategoryNotFoundException(Long id) {
        super("Categoria não encontrada com o id: " + id);
    }
}
