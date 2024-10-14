package com.mobisoft.mobisoftapi.models;

import java.util.Calendar;

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
    
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Employees seller;
    
    @Column(name="reference_date", nullable = false)
    private Calendar referenceDate;
    
    @Column(name="conclusion_pending", nullable = false)
    private boolean conclusionPending;
    
    @Column(name="notes", nullable = false)
    private String notes;
}
