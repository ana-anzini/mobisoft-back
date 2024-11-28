package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.category.CategoryDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void testCreateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setDescription("Test Category");

        Category createdCategory = new Category();
        createdCategory.setId(1L);
        createdCategory.setDescription("Test Category");

        when(categoryService.create(any(CategoryDTO.class))).thenReturn(createdCategory);

        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\": \"Test Category\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Test Category"));
    }

    @Test
    void testUpdateCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setDescription("Updated Category");

        Category updatedCategory = new Category();
        updatedCategory.setId(1L);
        updatedCategory.setDescription("Updated Category");

        when(categoryService.update(eq(1L), any(CategoryDTO.class))).thenReturn(updatedCategory);

        mockMvc.perform(put("/categories/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\": \"Updated Category\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Updated Category"));
    }

    @Test
    void testGetCategoryById() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setDescription("Test Category");

        when(categoryService.findById(1L)).thenReturn(category);

        mockMvc.perform(get("/categories/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Test Category"));
    }

    @Test
    void testFindAllCategories() throws Exception {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setDescription("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setDescription("Category 2");

        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/categories/findAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Category 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].description").value("Category 2"));
    }

    @Test
    void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).delete(1L);

        mockMvc.perform(delete("/categories/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).delete(1L);
    }

    @Test
    void testDeleteCategories() throws Exception {
        List<Long> categoryIds = Arrays.asList(1L, 2L);

        doNothing().when(categoryService).deleteCategories(categoryIds);

        mockMvc.perform(delete("/categories")
                .param("ids", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Categoria(s) deletada(s) com sucesso."));

        verify(categoryService, times(1)).deleteCategories(categoryIds);
    }
}