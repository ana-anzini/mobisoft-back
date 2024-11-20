package com.mobisoft.mobisoftapi.dtos.productproject;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductProjectDTO {

	private Long id;
	private Long projectId;
	private Long productId;
	private BigDecimal productValue;
}
