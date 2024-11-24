package com.mobisoft.mobisoftapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mobisoft.mobisoftapi.configs.exceptions.CategoryNotFoundException;
import com.mobisoft.mobisoftapi.dtos.category.CategoryDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.repositories.CategoryRepository;
import com.mobisoft.mobisoftapi.services.CategoryService;

public class CategoryServiceTest {
	
	@InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        Category category = new Category();
        category.setId(1L);
        category.setDescription("Test Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.findById(1L);
        assertNotNull(result);
        assertEquals("Test Category", result.getDescription());
    }

    @Test
    void testCreate() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setDescription("New Category");

        Category category = new Category();
        category.setDescription("New Category");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.create(categoryDTO);

        assertNotNull(result);
        assertEquals("New Category", result.getDescription());
    }

    @Test
    void testUpdate_Success() {
        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setDescription("Old Category");

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setDescription("Updated Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        Category result = categoryService.update(1L, categoryDTO);

        assertNotNull(result);
        assertEquals("Updated Category", result.getDescription());
    }

    @Test
    void testUpdate_NotFound() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setDescription("Updated Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.update(1L, categoryDTO));
    }

    @Test
    void testDelete_Success() {
        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);

        assertDoesNotThrow(() -> categoryService.delete(1L));
    }

    @Test
    void testDelete_NotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.delete(1L));
    }
}
