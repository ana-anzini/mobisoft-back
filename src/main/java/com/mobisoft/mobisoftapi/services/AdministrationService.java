package com.mobisoft.mobisoftapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.dtos.administration.AdministrationDTO;
import com.mobisoft.mobisoftapi.models.Administration;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.AdministrationRepository;

@Service
public class AdministrationService {

	@Autowired
	private AdministrationRepository administrationRepository;
	
	@Autowired
	private UserService userService;

	public Administration findById(Long id) {
		Optional<Administration> administration = administrationRepository.findById(id);
		return administration.orElseThrow();
	}
	
	public Administration findByUserGroup() {
		UserGroup userGroup = userService.getLoggedUser().getGroup();
		Administration administration = administrationRepository.findByUserGroup(userGroup);
		return administration;
	}

	public Administration create(AdministrationDTO administrationDTO) {
		UserGroup userGroup = userService.getLoggedUser().getGroup();
		Administration administration = new Administration();
		administration.setAdditionalSeller(administrationDTO.getAdditionalSeller());
		administration.setAdditionalProjectDesigner(administrationDTO.getAdditionalProjectDesigner());
		administration.setAdditionalFinancial(administrationDTO.getAdditionalFinancial());
		administration.setAdditionalAssembler(administrationDTO.getAdditionalAssembler());
		administration.setTax(administrationDTO.getTax());
		administration.setUserGroup(userGroup);
		administration.setCompanyName(administrationDTO.getCompanyName());
	    administration.setSocialReason(administrationDTO.getSocialReason());
	    administration.setAddress(administrationDTO.getAddress());
	    administration.setPhone(administrationDTO.getPhone());
	    administration.setEmail(administrationDTO.getEmail());
		return administrationRepository.save(administration);
	}

	public Optional<Administration> update(AdministrationDTO administrationDTO) {
        return Optional.of(findByUserGroup())
            .map(existingAdministration -> {
                existingAdministration.setAdditionalSeller(administrationDTO.getAdditionalSeller());
                existingAdministration.setAdditionalProjectDesigner(administrationDTO.getAdditionalProjectDesigner());
                existingAdministration.setAdditionalFinancial(administrationDTO.getAdditionalFinancial());
                existingAdministration.setAdditionalAssembler(administrationDTO.getAdditionalAssembler());
                existingAdministration.setTax(administrationDTO.getTax());
                existingAdministration.setCompanyName(administrationDTO.getCompanyName());
                existingAdministration.setSocialReason(administrationDTO.getSocialReason());
                existingAdministration.setAddress(administrationDTO.getAddress());
                existingAdministration.setPhone(administrationDTO.getPhone());
                existingAdministration.setEmail(administrationDTO.getEmail());
                return administrationRepository.save(existingAdministration);
            });
    }
}
