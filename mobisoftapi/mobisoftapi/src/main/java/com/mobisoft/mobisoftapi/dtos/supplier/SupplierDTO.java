package com.mobisoft.mobisoftapi.dtos.supplier;

import lombok.Data;

@Data
public class SupplierDTO {
	private Long id;
	private Long categoryId;
    private String name;
    private String cpfOrCnpj;
    private String phone;
    private String email;
    private String cep;
    private String address;
    private int number;
    private String neighborhood;
    private String additional;
}
