package com.mobisoft.mobisoftapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.configs.exceptions.CategoryNotFoundException;
import com.mobisoft.mobisoftapi.dtos.category.CategoryDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.models.Supplier;
import com.mobisoft.mobisoftapi.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
    private CategoryRepository categoryRepository;

    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow();
    }
    
    public Category create(CategoryDTO categoryDTO) {
    	Category category = new Category();
    	category.setDescription(categoryDTO.getDescription());
    	
    	return categoryRepository.save(category);
    }
    
    public Category update(Long id, CategoryDTO categoryDTO) {
    	Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        existingCategory.setDescription(categoryDTO.getDescription());
        return categoryRepository.save(existingCategory);
    }
    
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.delete(category);
    }
    
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    public void deleteCategories(List<Long> ids) {
        List<Category> categories = categoryRepository.findAllById(ids);
        categoryRepository.deleteAll(categories);
    }
}
