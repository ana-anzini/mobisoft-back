package com.mobisoft.mobisoftapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.dtos.supplier.SupplierDTO;
import com.mobisoft.mobisoftapi.models.Supplier;
import com.mobisoft.mobisoftapi.repositories.SupplierRepository;

@Service
public class SupplierService {

	@Autowired
    private SupplierRepository supplierRepository;

    public Supplier findById(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.orElseThrow();
    }
    
    public Supplier createSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setSupplierType(supplierDTO.getSupplierType());
        supplier.setName(supplierDTO.getName());
        supplier.setCpfOrCnpj(supplierDTO.getCpfOrCnpj());
        supplier.setPhone(supplierDTO.getPhone());
        supplier.setEmail(supplierDTO.getEmail());
        supplier.setCep(supplierDTO.getCep());
        supplier.setAddress(supplierDTO.getAddress());
        supplier.setNumber(supplierDTO.getNumber());
        supplier.setNeighborhood(supplierDTO.getNeighborhood());
        supplier.setAdditional(supplierDTO.getAdditional());

        return supplierRepository.save(supplier);
    }
}
