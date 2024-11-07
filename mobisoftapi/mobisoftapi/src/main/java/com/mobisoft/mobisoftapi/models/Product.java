package com.mobisoft.mobisoftapi.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "PRODUCTS")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
    @Column(name="description", nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

	@Column(name="quantity", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer quantity;
	
    @Column(name="product_value", nullable = true)
    private BigDecimal productValue;
}
