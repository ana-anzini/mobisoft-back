package com.mobisoft.mobisoftapi.models;

import java.math.BigDecimal;
import java.util.Calendar;

import com.mobisoft.mobisoftapi.enums.payment.PaymentType;
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
@Table(name = "FINANCIAL")
public class Financial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
    @Column(name="installments_number", nullable = true)
    private int installmentsNumber;
    
    @Column(name="first_payment", nullable = true)
    private Calendar firstPayment;
    
    @Column(name="payment_type", nullable = true)
    @Enumerated(EnumType.ORDINAL)
    private PaymentType paymentType;
    
    @Column(name="discount", nullable = true)
    private BigDecimal discount;
    
    @Column(name="additional_expenses", nullable = true)
    private BigDecimal additionalExpenses;
    
    @Column(name="freight", nullable = true)
    private BigDecimal freight;
    
    @Column(name="total_value", nullable = true)
    private BigDecimal totalValue;
    
    @Column(name="total_custs", nullable = false)
    private BigDecimal totalCusts;
    
    @Column(name="total_projectdesigner", nullable = true)
    private BigDecimal totalProjectDesigner;
    
    @Column(name="total_seller", nullable = true)
    private BigDecimal totalSeller;
    
    @Column(name="total_profit", nullable = true)
    private BigDecimal totalProfit;
    
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
