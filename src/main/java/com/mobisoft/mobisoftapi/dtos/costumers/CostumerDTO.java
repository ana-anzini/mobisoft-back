package com.mobisoft.mobisoftapi.dtos.costumers;

import java.util.Calendar;

import com.mobisoft.mobisoftapi.enums.costumer.NetworkType;
import com.mobisoft.mobisoftapi.enums.costumer.PersonType;

import lombok.Data;

@Data
public class CostumerDTO {
	private String name;
    private String cpfOrCnpj;
    private String phone;
    private String email;
    private String cep;
    private String address;
    private int number;
    private String neighborhood;
    private String additional;
    private String rg;
    private Calendar birthday;
    private NetworkType networkType;
    private PersonType personType;
    private String notes;
}
