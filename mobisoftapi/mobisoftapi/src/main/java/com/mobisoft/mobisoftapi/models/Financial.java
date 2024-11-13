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
	
    @Column(name="installments_number", nullable = false)
    private int installmentsNumber;
    
    @Column(name="first_payment", nullable = false)
    private Calendar firstPayment;
    
    @Column(name="payment_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PaymentType paymentType;
    
    @Column(name="discount", nullable = false)
    private BigDecimal discount;
    
    @Column(name="additional_expenses", nullable = false)
    private BigDecimal additionalExpenses;
    
    @Column(name="additional", nullable = false)
    private boolean additional;
    
    @Column(name="additional_seller", nullable = false)
    private BigDecimal additionalSeller;
    
    @Column(name="additional_projectdesigner", nullable = false)
    private BigDecimal additionalProjectDesigner;
    
    @Column(name="additional_financial", nullable = false)
    private BigDecimal additionalFinancial;
    
    @Column(name="freight", nullable = false)
    private BigDecimal freight;
    
    @Column(name="total_value", nullable = false)
    private BigDecimal totalValue;
    
    @Column(name="total_custs", nullable = false)
    private BigDecimal totalCusts;
    
    @Column(name="total_projectdesigner", nullable = false)
    private BigDecimal totalProjectDesigner;
    
    @Column(name="total_seller", nullable = false)
    private BigDecimal totalSeller;
    
    @Column(name="total_profit", nullable = false)
    private BigDecimal totalProfit;
    
    @Column(name="financial_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusType statusType;
}
