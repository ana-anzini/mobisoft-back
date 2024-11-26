package com.mobisoft.mobisoftapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobisoft.mobisoftapi.dtos.financial.FinancialDTO;
import com.mobisoft.mobisoftapi.models.Financial;
import com.mobisoft.mobisoftapi.services.FinancialService;

@RestController
@RequestMapping("/financial")
public class FinancialController {

    @Autowired
    private FinancialService financialService;

    @PostMapping
    public ResponseEntity<Financial> createFinancial(@RequestBody FinancialDTO financialDTO) {
        Financial newFinancial = financialService.createFinancial(financialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFinancial);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Financial> getFinancialById(@PathVariable Long id) {
        Financial financial = financialService.findById(id);
        return ResponseEntity.ok(financial);
    }
    
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<Financial> getFinancialByProjectId(@PathVariable Long projectId) {
        Financial financial = financialService.findByProjectId(projectId);
        return ResponseEntity.ok(financial);
    }
    
    @GetMapping("/findTotalValues/{projectId}")
    public ResponseEntity<Financial> getCalculatedValues(@PathVariable Long projectId) {
        Financial financial = financialService.calculateTotalProject(projectId);
        return ResponseEntity.ok(financial);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Financial> updateFinancial(@PathVariable Long projectId, @RequestBody FinancialDTO financialDTO) {
        Financial updatedFinancial = financialService.updateFinancial(projectId, financialDTO);
        return ResponseEntity.ok(updatedFinancial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinancial(@PathVariable Long id) {
        financialService.deleteFinancial(id);
        return ResponseEntity.noContent().build();
    }
}
