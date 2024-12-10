package com.mobisoft.mobisoftapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CATEGORY")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
    @Column(name="description", nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private UserGroup userGroup;
    
    public Category(String description, UserGroup userGroup) {
        this.description = description;
        this.userGroup = userGroup;
    }
}
