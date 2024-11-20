package com.mobisoft.mobisoftapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobisoft.mobisoftapi.dtos.administration.AdministrationDTO;
import com.mobisoft.mobisoftapi.models.Administration;
import com.mobisoft.mobisoftapi.services.AdministrationService;

@RestController
@RequestMapping("/administration")
public class AdministrationController {

	@Autowired
	private AdministrationService administrationService;
	
    @PostMapping
    public ResponseEntity<Administration> createAdministration(@RequestBody AdministrationDTO administrationDTO) {
        Administration newAdministration = administrationService.create(administrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAdministration);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Optional<Administration>> updateAdministration(@PathVariable Long id, @RequestBody AdministrationDTO administrationDTO) {
        Optional<Administration> updateAdministration = administrationService.update(id, administrationDTO);
        return ResponseEntity.ok(updateAdministration);
    }
}
