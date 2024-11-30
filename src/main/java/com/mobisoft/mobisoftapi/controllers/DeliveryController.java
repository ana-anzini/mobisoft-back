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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobisoft.mobisoftapi.dtos.delivery.DeliveryDTO;
import com.mobisoft.mobisoftapi.models.Deliveries;
import com.mobisoft.mobisoftapi.models.Financial;
import com.mobisoft.mobisoftapi.services.DeliveriesService;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

	@Autowired
	private DeliveriesService deliveriesService;
	
    @PostMapping
    public ResponseEntity<Deliveries> createDelivery(@RequestBody DeliveryDTO DeliveryDTO) {
        Deliveries newDelivery = deliveriesService.createDelivery(DeliveryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDelivery);
    }

    @GetMapping
    public ResponseEntity<List<Deliveries>> getAllDeliveries() {
        List<Deliveries> deliveries = deliveriesService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("find/{projectId}")
    public ResponseEntity<Deliveries> getDelivery(@PathVariable Long projectId) {
        Deliveries delivery = deliveriesService.findByProjectId(projectId);
        return ResponseEntity.ok(delivery);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deliveries> getDeliveryById(@PathVariable Long id) {
        Deliveries delivery = deliveriesService.findById(id);
        return ResponseEntity.ok(delivery);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Deliveries> updateDelivery(@PathVariable Long projectId, @RequestBody DeliveryDTO DeliveryDTO) {
        Deliveries updatedDelivery = deliveriesService.updateDelivery(projectId, DeliveryDTO);
        return ResponseEntity.ok(updatedDelivery);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteDelivery(@RequestParam List<Long> ids) {
        deliveriesService.deleteDeliveries(ids);
        return ResponseEntity.noContent().build();
    }
}
