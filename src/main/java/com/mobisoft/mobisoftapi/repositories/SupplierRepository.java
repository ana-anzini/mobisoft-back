package com.mobisoft.mobisoftapi.repositories;

import com.mobisoft.mobisoftapi.dtos.supplier.SupplierDTO;
import com.mobisoft.mobisoftapi.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT new com.mobisoft.mobisoftapi.dtos.supplier.SupplierDTO(s.id, s.name, s.cpfOrCnpj, s.phone, s.email, s.cep, s.address, s.number, s.neighborhood, s.additional, c.id, c.description, c.code) " +
           "FROM Supplier s JOIN s.category c")
    List<SupplierDTO> findAllSuppliersWithCategoryDescription();
    
    @Query("SELECT new com.mobisoft.mobisoftapi.dtos.supplier.SupplierDTO(s.id, s.name, s.cpfOrCnpj, s.phone, s.email, s.cep, s.address, s.number, s.neighborhood, s.additional, c.id, c.description, c.code) " +
           "FROM Supplier s JOIN s.category c WHERE s.id = :id")
    SupplierDTO findSupplierWithCategoryDescriptionById(Long id);
    
    List<Supplier> findByUserGroupId(Long userGroupId);
    
    Supplier findByCode(String code);
}
