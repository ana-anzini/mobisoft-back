package com.mobisoft.mobisoftapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.configs.exceptions.ProductNotFoundException;
import com.mobisoft.mobisoftapi.dtos.products.ProductDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.models.Product;
import com.mobisoft.mobisoftapi.models.Supplier;
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
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        
        Category category = categoryService.findById(productDTO.getCategoryId());
        Supplier supplier = supplierService.findById(productDTO.getSupplierId());
        
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setSupplier(supplier);
        existingProduct.setCategory(category);
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setProductValue(productDTO.getProductValue());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
    
    public void deleteProducts(List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);
        productRepository.deleteAll(products);
    }
}
