package com.mobisoft.mobisoftapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.dtos.administration.AdministrationDTO;
import com.mobisoft.mobisoftapi.models.Administration;
import com.mobisoft.mobisoftapi.repositories.AdministrationRepository;

@Service
public class AdministrationService {

	@Autowired
	private AdministrationRepository administrationRepository;

	public Administration findById(Long id) {
		Optional<Administration> administration = administrationRepository.findById(id);
		return administration.orElseThrow();
	}

	public Administration create(AdministrationDTO administrationDTO) {
		Administration administration = new Administration();
		administration.setAdditionalSeller(administrationDTO.getAdditionalSeller());
		administration.setAdditionalProjectDesigner(administrationDTO.getAdditionalProjectDesigner());
		administration.setAdditionalFinancial(administrationDTO.getAdditionalFinancial());
		administration.setAdditionalAssembler(administrationDTO.getAdditionalAssembler());
		administration.setTax(administrationDTO.getTax());
		return administrationRepository.save(administration);
	}

	public Optional<Administration> update(Long id, AdministrationDTO administrationDTO) {
	    return administrationRepository.findById(id)
	        .map(existingAdministration -> {
	            existingAdministration.setAdditionalSeller(administrationDTO.getAdditionalSeller());
	            existingAdministration.setAdditionalProjectDesigner(administrationDTO.getAdditionalProjectDesigner());
	            existingAdministration.setAdditionalFinancial(administrationDTO.getAdditionalFinancial());
	            existingAdministration.setAdditionalAssembler(administrationDTO.getAdditionalAssembler());
	            existingAdministration.setTax(administrationDTO.getTax());
	            return administrationRepository.save(existingAdministration);
	        });
	}
}
