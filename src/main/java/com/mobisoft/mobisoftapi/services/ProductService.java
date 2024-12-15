package com.mobisoft.mobisoftapi.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mobisoft.mobisoftapi.configs.exceptions.ProductNotFoundException;
import com.mobisoft.mobisoftapi.dtos.products.ProductDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.models.Product;
import com.mobisoft.mobisoftapi.models.Supplier;
import com.mobisoft.mobisoftapi.models.UserGroup;
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
	
	@Autowired
	private UserService userService;

	@Transactional
	public Product createProduct(ProductDTO productDTO) {
		UserGroup userGroup = userService.getLoggedUser().getGroup();
		Product product = new Product();
		product.setDescription(productDTO.getDescription());
		product.setProductValue(productDTO.getProductValue());
		product.setSupplier(supplierService.findById(productDTO.getSupplierId()));
		product.setCategory(categoryService.findById(productDTO.getCategoryId()));
		product.setUserGroup(userGroup);

		return productRepository.save(product);
	}

	public List<Product> getAllProducts() {
		UserGroup userGroup = userService.getLoggedUser().getGroup();
		return productRepository.findByUserGroupId(userGroup.getId());
	}

	public Product getProductById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}
	
	public List<Product> getProductsByCategory(Long categoryId) {
	    Category category = categoryService.findById(categoryId);
	    return productRepository.findByCategory(category);
	}

	public Product updateProduct(Long id, ProductDTO productDTO) {
		Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

		Category category = categoryService.findById(productDTO.getCategoryId());
		Supplier supplier = supplierService.findById(productDTO.getSupplierId());

		existingProduct.setDescription(productDTO.getDescription());
		existingProduct.setSupplier(supplier);
		existingProduct.setCategory(category);
		existingProduct.setProductValue(productDTO.getProductValue());

		return productRepository.save(existingProduct);
	}

	public void deleteProduct(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
		productRepository.delete(product);
	}

	public void deleteProducts(List<Long> ids) {
		List<Product> products = productRepository.findAllById(ids);
		productRepository.deleteAll(products);
	}
	
	@Transactional
	public void importProductsFromCSV(MultipartFile file) {
	    List<Product> products = new ArrayList<>();
	    UserGroup userGroup = userService.getLoggedUser().getGroup();

	    try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
	        String line;
	        int lineNumber = 0;

	        while ((line = reader.readLine()) != null) {
	            lineNumber++;
	            if (lineNumber == 1) {
	                continue;
	            }

	            String[] values = line.split(",");

	            if (values.length < 4) {
	                throw new IllegalArgumentException("CSV inválido na linha " + lineNumber);
	            }

	            String categoryCode = values[0].trim();
	            Category category = categoryService.findByCode(categoryCode);
	            if (category == null) {
	                throw new IllegalArgumentException("Categoria não encontrada para o código: " + categoryCode + " (linha " + lineNumber + ")");
	            }

	            String supplierCode = values[1].trim();
	            Supplier supplier = supplierService.findByCode(supplierCode);
	            if (supplier == null) {
	                throw new IllegalArgumentException("Fornecedor não encontrado para o código: " + supplierCode + " (linha " + lineNumber + ")");
	            }

	            String productDescription = values[2].trim();
	            String value = values[3].trim();

	            Product product = new Product();
	            product.setDescription(productDescription);
	            product.setCategory(category);
	            product.setSupplier(supplier);
	            product.setProductValue(new BigDecimal(value));
	            product.setUserGroup(userGroup);

	            products.add(product);
	        }

	        productRepository.saveAll(products);
	    } catch (IOException e) {
	        throw new RuntimeException("Erro ao processar o arquivo CSV", e);
	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("Erro ao converter dados do CSV", e);
	    }
	}
}
