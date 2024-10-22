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

import com.mobisoft.mobisoftapi.dtos.delivery.DeliveryDTO;
import com.mobisoft.mobisoftapi.models.Deliveries;
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

    @GetMapping("/{id}")
    public ResponseEntity<Deliveries> getDeliveryById(@PathVariable Long id) {
        Deliveries delivery = deliveriesService.findById(id);
        return ResponseEntity.ok(delivery);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deliveries> updateDelivery(@PathVariable Long id, @RequestBody DeliveryDTO DeliveryDTO) {
        Deliveries updatedDelivery = deliveriesService.updateDelivery(id, DeliveryDTO);
        return ResponseEntity.ok(updatedDelivery);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveriesService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }
}
