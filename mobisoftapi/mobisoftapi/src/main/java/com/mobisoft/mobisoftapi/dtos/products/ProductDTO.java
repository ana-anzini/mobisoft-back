package com.mobisoft.mobisoftapi.dtos.products;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String description;
    private Long supplierId;
    private Long categoryId;
    private Integer quantity;
    private BigDecimal productValue;
}
