package com.mobisoft.mobisoftapi.dtos.administration;

import java.math.BigDecimal;

import com.mobisoft.mobisoftapi.models.UserGroup;

import lombok.Data;

@Data
public class AdministrationDTO {

    private BigDecimal additionalSeller;
    private BigDecimal additionalProjectDesigner;
    private BigDecimal additionalFinancial;
    private BigDecimal additionalAssembler;
    private BigDecimal tax;
    private UserGroup userGroup;
    private String companyName;
    private String socialReason;
    private String address;
    private String phone;
    private String email;
}
