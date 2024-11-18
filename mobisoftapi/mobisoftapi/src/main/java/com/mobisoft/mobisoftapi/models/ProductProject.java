package com.mobisoft.mobisoftapi.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "PRODUCT_PROJECT")
public class ProductProject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="project_id", nullable = false)
	private Project project;
	
	@ManyToOne
	@JoinColumn(name="product_id", nullable = false)
	private Product product;
	
	@Column(name="product_value", nullable = false)
    private BigDecimal productValue;
}
