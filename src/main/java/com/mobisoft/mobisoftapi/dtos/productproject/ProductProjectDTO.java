package com.mobisoft.mobisoftapi.dtos.productproject;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductProjectDTO {

	private Long id;
	private Long projectId;
	private Long productId;
	private BigDecimal productValue;
}
