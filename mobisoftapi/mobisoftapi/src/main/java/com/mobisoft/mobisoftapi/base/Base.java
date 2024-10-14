package com.mobisoft.mobisoftapi.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class Base {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name="name", nullable = false)
    private String name;
	
    @Column(name="cpf_cnpj", nullable = false)
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
