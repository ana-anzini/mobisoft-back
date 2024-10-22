package com.mobisoft.mobisoftapi.configs.exceptions;

public class DeliveryNotFoundException extends RuntimeException {

	public DeliveryNotFoundException(Long id) {
        super("Entrega não encontrada com o id: " + id);
    }
}
