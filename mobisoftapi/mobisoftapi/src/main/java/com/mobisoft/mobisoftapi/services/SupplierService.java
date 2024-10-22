package com.mobisoft.mobisoftapi.services;

import java.util.List;
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
    
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier updateSupplier(Long id, SupplierDTO supplierDTO) {
        Supplier existingSupplier = findById(id);
        existingSupplier.setName(supplierDTO.getName());
        existingSupplier.setCpfOrCnpj(supplierDTO.getCpfOrCnpj());
        existingSupplier.setPhone(supplierDTO.getPhone());
        existingSupplier.setEmail(supplierDTO.getEmail());
        existingSupplier.setCep(supplierDTO.getCep());
        existingSupplier.setAddress(supplierDTO.getAddress());
        existingSupplier.setNumber(supplierDTO.getNumber());
        existingSupplier.setNeighborhood(supplierDTO.getNeighborhood());
        existingSupplier.setAdditional(supplierDTO.getAdditional());
        existingSupplier.setSupplierType(supplierDTO.getSupplierType());

        return supplierRepository.save(existingSupplier);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = findById(id);
        supplierRepository.delete(supplier);
    }
}
