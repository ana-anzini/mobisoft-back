package com.mobisoft.mobisoftapi.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ADMINISTRATION")
public class Administration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
    @Column(name="additional_seller", nullable = false)
    private BigDecimal additionalSeller;
    
    @Column(name="additional_projectdesigner", nullable = false)
    private BigDecimal additionalProjectDesigner;
    
    @Column(name="additional_financial", nullable = false)
    private BigDecimal additionalFinancial;
    
    @Column(name="additional_assembler", nullable = false)
    private BigDecimal additionalAssembler;
    
    @Column(name="tax", nullable = false)
    private BigDecimal tax;
}
