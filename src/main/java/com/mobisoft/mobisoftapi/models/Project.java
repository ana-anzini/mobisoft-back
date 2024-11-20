package com.mobisoft.mobisoftapi.models;

import java.util.Calendar;
import java.util.List;

import com.mobisoft.mobisoftapi.enums.project.StatusType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "PROJECTS")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
    @Column(name="description", nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "costumer_id", nullable = false)
    private Costumer costumer;
    
    @ManyToOne
    @JoinColumn(name = "project_designer_id", nullable = false)
    private Employees projectDesigner;
    
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deliveries> deliveries;
    
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Employees seller;
    
    @OneToOne
    @JoinColumn(name = "financial_id", nullable = true)
    private Financial financial;
    
    @Column(name="reference_date", nullable = false)
    private Calendar referenceDate;
    
    @Column(name="conclusion_pending", nullable = true)
    private boolean conclusionPending;
    
    @Column(name="notes", nullable = true)
    private String notes;
    
    @Column(name="financial_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusType financialStatus;
    
    @Column(name="delivery_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusType deliveryStatus;
}
