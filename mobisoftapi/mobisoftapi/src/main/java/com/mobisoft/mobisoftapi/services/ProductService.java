package com.mobisoft.mobisoftapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.dtos.products.ProductDTO;
import com.mobisoft.mobisoftapi.models.Product;
import com.mobisoft.mobisoftapi.repositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
	
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private CategoryService categoryService;

    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setProductValue(productDTO.getProductValue());
        product.setSupplier(supplierService.findById(productDTO.getSupplierId()));
        product.setCategory(categoryService.findById(productDTO.getCategoryId()));

        return productRepository.save(product);
    }
}
