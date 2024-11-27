package com.mobisoft.mobisoftapi.dtos.productproject;

import java.math.BigDecimal;
import java.util.List;

import com.mobisoft.mobisoftapi.models.ProductProject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductProjectDetailsDTO {
	private List<ProductProject> products;
    private BigDecimal totalValue;
}
