package com.mobisoft.mobisoftapi.models;

import java.util.Calendar;

import com.mobisoft.mobisoftapi.base.Base;
import com.mobisoft.mobisoftapi.enums.costumer.NetworkType;
import com.mobisoft.mobisoftapi.enums.costumer.PersonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "COSTUMER")
public class Costumer extends Base {
	
    @Column(name="rg", nullable = true)
    private String rg;
    
    @Column(name="birthday", nullable = true)
    private Calendar birthday;
    
    @Column(name="network_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private NetworkType networkType;
    
    @Column(name="person_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PersonType personType;
    
    @Column(name="notes", nullable = false)
    private String notes;
	
}
