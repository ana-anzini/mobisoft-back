package com.mobisoft.mobisoftapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.dtos.supplier.SupplierDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.models.Supplier;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.SupplierRepository;

@Service
public class SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;

	public Supplier findById(Long id) {
		Optional<Supplier> supplier = supplierRepository.findById(id);
		return supplier.orElseThrow();
	}

	public Supplier createSupplier(SupplierDTO supplierDTO) {
		UserGroup userGroup = userService.getLoggedUser().getGroup();
		Supplier supplier = new Supplier();
		Optional<Category> categoryOpt = Optional.ofNullable(categoryService.findById(supplierDTO.getCategoryId()));
		if (categoryOpt.isPresent()) {
			supplier.setCategory(categoryOpt.get());
		}
		supplier.setName(supplierDTO.getName());
		supplier.setCpfOrCnpj(supplierDTO.getCpfOrCnpj());
		supplier.setPhone(supplierDTO.getPhone());
		supplier.setEmail(supplierDTO.getEmail());
		supplier.setCep(supplierDTO.getCep());
		supplier.setAddress(supplierDTO.getAddress());
		supplier.setNumber(supplierDTO.getNumber());
		supplier.setNeighborhood(supplierDTO.getNeighborhood());
		supplier.setAdditional(supplierDTO.getAdditional());
		supplier.setUserGroup(userGroup);
		supplier.setCode(supplierDTO.getCode());

		return supplierRepository.save(supplier);
	}

	public List<Supplier> getAllSuppliers() {
		UserGroup userGroup = userService.getLoggedUser().getGroup();
		return supplierRepository.findByUserGroupId(userGroup.getId());
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
		existingSupplier.setCategory(categoryService.findById(supplierDTO.getCategoryId()));
		existingSupplier.setCode(supplierDTO.getCode());

		return supplierRepository.save(existingSupplier);
	}

	public void deleteSuppliers(List<Long> ids) {
		List<Supplier> suppliers = supplierRepository.findAllById(ids);
		supplierRepository.deleteAll(suppliers);
	}
	
	public Supplier findByCode(String code) {
		return supplierRepository.findByCode(code);
	}
}
