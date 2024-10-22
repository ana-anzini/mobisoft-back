package com.mobisoft.mobisoftapi.controllers;

import java.util.List;

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

import com.mobisoft.mobisoftapi.dtos.costumers.CostumerDTO;
import com.mobisoft.mobisoftapi.models.Costumer;
import com.mobisoft.mobisoftapi.services.CostumerService;

@RestController
@RequestMapping("/costumers")
public class CostumerController {

    @Autowired
    private CostumerService costumerService;

    @PostMapping
    public ResponseEntity<Costumer> createCostumer(@RequestBody CostumerDTO costumerDTO) {
        Costumer newCostumer = costumerService.createCostumer(costumerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCostumer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Costumer> getCostumerById(@PathVariable Long id) {
        Costumer costumer = costumerService.getCostumerById(id);
        return ResponseEntity.ok(costumer);
    }

    @GetMapping
    public ResponseEntity<List<Costumer>> getAllCostumers() {
        List<Costumer> costumers = costumerService.getAllCostumers();
        return ResponseEntity.ok(costumers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Costumer> updateCostumer(@PathVariable Long id, @RequestBody CostumerDTO costumerDTO) {
        Costumer updatedCostumer = costumerService.updateCostumer(id, costumerDTO);
        return ResponseEntity.ok(updatedCostumer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCostumer(@PathVariable Long id) {
        costumerService.deleteCostumer(id);
        return ResponseEntity.noContent().build();
    }
}
