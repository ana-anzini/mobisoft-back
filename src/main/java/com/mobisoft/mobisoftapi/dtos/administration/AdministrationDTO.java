package com.mobisoft.mobisoftapi.dtos.administration;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AdministrationDTO {

    private BigDecimal additionalSeller;
    private BigDecimal additionalProjectDesigner;
    private BigDecimal additionalFinancial;
    private BigDecimal additionalAssembler;
    private BigDecimal tax;
}
