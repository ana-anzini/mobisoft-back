package com.mobisoft.mobisoftapi.dtos.supplier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierDTO {
    private Long id;
    private String name;
    private String cpfOrCnpj;
    private String phone;
    private String email;
    private String cep;
    private String address;
    private int number;
    private String neighborhood;
    private String additional;
    private Long categoryId;
    private String categoryDescription;
}
