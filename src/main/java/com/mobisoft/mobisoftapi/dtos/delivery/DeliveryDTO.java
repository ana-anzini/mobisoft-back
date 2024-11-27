package com.mobisoft.mobisoftapi.dtos.delivery;

import java.math.BigDecimal;
import java.util.Calendar;

import com.mobisoft.mobisoftapi.enums.project.StatusType;
import com.mobisoft.mobisoftapi.models.UserGroup;

import lombok.Data;

@Data
public class DeliveryDTO {
	private Long projectId;
    private boolean addressClient;
    private String cep;
    private String address;
    private int number;
    private String neighborhood;
    private String additional;
    private Calendar deliveryDate;
    private BigDecimal freight;
    private UserGroup userGroup;
}
