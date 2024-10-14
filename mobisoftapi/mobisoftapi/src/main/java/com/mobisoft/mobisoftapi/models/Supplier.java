package com.mobisoft.mobisoftapi.models;

import com.mobisoft.mobisoftapi.base.Base;
import com.mobisoft.mobisoftapi.enums.suppliers.SupplierType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "SUPPLIER")
public class Supplier extends Base {
	
    @Column(name="supplier_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SupplierType supplierType;
}
