package com.mobisoft.mobisoftapi.dtos.products;

import java.math.BigDecimal;

import com.mobisoft.mobisoftapi.models.UserGroup;

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
    private BigDecimal productValue;
    private UserGroup userGroup;
}
