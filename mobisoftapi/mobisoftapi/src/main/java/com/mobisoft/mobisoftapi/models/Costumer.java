package com.mobisoft.mobisoftapi.models;

import java.util.Calendar;

import com.mobisoft.mobisoftapi.base.Base;
import com.mobisoft.mobisoftapi.enums.costumer.NetworkType;
import com.mobisoft.mobisoftapi.enums.employees.EmployeesType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "COSTUMER")
public class Costumer extends Base {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(name="rg", nullable = false)
    private String rg;
    
    @Column(name="birthday", nullable = false)
    private Calendar birthday;
    
    @Column(name="network_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private NetworkType networkType;
    
    @Column(name="notes", nullable = false)
    private String notes;
	
}
