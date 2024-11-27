package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.category.CategoryDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
        category = new Category(1L, "Test Category", null);
        categoryDTO = new CategoryDTO(null, "Test Category");
    }

    @Test
    void testCreateCategory() {
        when(categoryService.create(categoryDTO)).thenReturn(category);

        ResponseEntity<Category> response = categoryController.createCategory(categoryDTO);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).create(categoryDTO);
    }

    @Test
    void testUpdateCategory() {
        when(categoryService.update(1L, categoryDTO)).thenReturn(category);

        ResponseEntity<Category> response = categoryController.updateCategory(1L, categoryDTO);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
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
    void testFindAll() {
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
    void testDeleteCategories_Success() {
        doNothing().when(categoryService).deleteCategories(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = categoryController.deleteCategories(Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Categoria(s) deletada(s) com sucesso.", response.getBody());
        verify(categoryService, times(1)).deleteCategories(Arrays.asList(1L, 2L));
    }

    @Test
    void testDeleteCategories_DataIntegrityViolation() {
        doThrow(new DataIntegrityViolationException("")).when(categoryService).deleteCategories(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = categoryController.deleteCategories(Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Não é possível excluir esta categoria, pois ela está em uso.", response.getBody());
        verify(categoryService, times(1)).deleteCategories(Arrays.asList(1L, 2L));
    }

    @Test
    void testDeleteCategories_GeneralException() {
        doThrow(new RuntimeException("General error")).when(categoryService).deleteCategories(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = categoryController.deleteCategories(Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Erro ao processar a solicitação.", response.getBody());
        verify(categoryService, times(1)).deleteCategories(Arrays.asList(1L, 2L));
    }
}