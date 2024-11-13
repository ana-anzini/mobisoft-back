package com.mobisoft.mobisoftapi.models;

import java.util.Calendar;

import com.mobisoft.mobisoftapi.enums.project.StatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "DELIVERIES")
public class Deliveries {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
    
    @Column(name="address_client", nullable = true)
    private boolean addressClient;
    
    @Column(name="cep", nullable = false)
    private String cep;
    
    @Column(name="address", nullable = false)
    private String address;
    
    @Column(name="number", nullable = false)
    private int number;
    
    @Column(name="neighborhood", nullable = false)
    private String neighborhood;
    
    @Column(name="additional", nullable = false)
    private String additional;
	
    @Column(name="delivery_date", nullable = false)
    private Calendar deliveryDate;
    
    @Column(name="delivery_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusType statusType;
}
