package com.mobisoft.mobisoftapi.configs.exceptions;

public class ProductProjectNotFoundException extends RuntimeException {

	public ProductProjectNotFoundException(Long id) {
        super("Produto do Projeto não encontrado com o id: " + id);
    }
}
