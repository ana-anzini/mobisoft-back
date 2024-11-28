package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.category.CategoryDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setId(1L);
        category.setDescription("Test Category");

        categoryDTO = new CategoryDTO();
        categoryDTO.setDescription("Test Category");
    }

    @Test
    void testCreateCategory() {
        when(categoryService.create(any(CategoryDTO.class))).thenReturn(category);

        ResponseEntity<Category> response = categoryController.createCategory(categoryDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).create(categoryDTO);
    }

    @Test
    void testUpdateCategory() {
        when(categoryService.update(eq(1L), any(CategoryDTO.class))).thenReturn(category);

        ResponseEntity<Category> response = categoryController.updateCategory(1L, categoryDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).update(1L, categoryDTO);
    }

    @Test
    void testGetCategoryById() {
        when(categoryService.findById(1L)).thenReturn(category);

        ResponseEntity<Category> response = categoryController.getCategoryById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).findById(1L);
    }

    @Test
    void testFindAllCategories() {
        List<Category> categories = Arrays.asList(category);
        when(categoryService.findAll()).thenReturn(categories);

        ResponseEntity<List<Category>> response = categoryController.findAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(categories, response.getBody());
        verify(categoryService, times(1)).findAll();
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryService).delete(1L);

        ResponseEntity<Void> response = categoryController.deleteCategory(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(categoryService, times(1)).delete(1L);
    }

    @Test
    void testDeleteCategories() {
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        doNothing().when(categoryService).deleteCategories(categoryIds);

        ResponseEntity<String> response = categoryController.deleteCategories(categoryIds);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Categoria(s) deletada(s) com sucesso.", response.getBody());
        verify(categoryService, times(1)).deleteCategories(categoryIds);
    }
}