package com.mobisoft.mobisoftapi.configs.exceptions;

public class ProjectNotFoundException extends RuntimeException {

	public ProjectNotFoundException(Long id) {
        super("Projeto não encontrado com o id: " + id);
    }
}
