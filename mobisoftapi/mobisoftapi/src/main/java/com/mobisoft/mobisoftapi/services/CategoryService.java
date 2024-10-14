package com.mobisoft.mobisoftapi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.dtos.category.CategoryDTO;
import com.mobisoft.mobisoftapi.models.Category;
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
}
