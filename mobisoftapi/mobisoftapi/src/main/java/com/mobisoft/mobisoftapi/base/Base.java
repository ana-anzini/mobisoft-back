package com.mobisoft.mobisoftapi.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Base {

	@Column(name="name", nullable = false)
    private String name;
	
    @Column(name="cpfOrCnpj", nullable = false)
    private String cpfOrCnpj;
	
    @Column(name="phone", nullable = false)
    private String phone;
    
    @Column(name="email", nullable = false)
    private String email;
    
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
}
