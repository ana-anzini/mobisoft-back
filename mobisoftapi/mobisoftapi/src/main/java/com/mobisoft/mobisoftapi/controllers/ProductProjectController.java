package com.mobisoft.mobisoftapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.mobisoft.mobisoftapi.dtos.productproject.ProductProjectDTO;
import com.mobisoft.mobisoftapi.dtos.products.ProductDTO;
import com.mobisoft.mobisoftapi.models.Product;
import com.mobisoft.mobisoftapi.models.ProductProject;
import com.mobisoft.mobisoftapi.services.ProductProjectService;
import com.mobisoft.mobisoftapi.services.ProductService;

@RestController
@RequestMapping("/productProjects")
public class ProductProjectController {

	@Autowired
    private ProductProjectService productProjectService;

    @PostMapping
    public ResponseEntity<ProductProject> createProductProject(@RequestBody ProductProjectDTO productProjectDTO) {
        ProductProject createdProductProject = productProjectService.createProductProject(productProjectDTO);
        return new ResponseEntity<>(createdProductProject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductProject> getProductProjectById(@PathVariable Long id) {
        ProductProject productProject = productProjectService.findById(id);
        return ResponseEntity.ok(productProject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductProject> updateProductProject(@PathVariable Long id, @RequestBody ProductProjectDTO productProjectDTO) {
        ProductProject updatedProductProject = productProjectService.updateProductProject(id, productProjectDTO);
        return ResponseEntity.ok(updatedProductProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductProject(@PathVariable Long id) {
    	productProjectService.deleteProductProject(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteProductProjects(@RequestParam List<Long> ids) {
        productProjectService.deleteProductProjects(ids);
        return ResponseEntity.ok("Produto(s) deletada(s) com sucesso.");
    }
}
