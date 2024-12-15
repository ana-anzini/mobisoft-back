package com.mobisoft.mobisoftapi.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mobisoft.mobisoftapi.dtos.category.CategoryDTO;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDTOTest {

    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        categoryDTO = new CategoryDTO();
    }

    @Test
    void testCategoryDTOCreation() {
        categoryDTO.setId(1L);
        categoryDTO.setDescription("Test Category");

        assertNotNull(categoryDTO);
        assertEquals(1L, categoryDTO.getId());
        assertEquals("Test Category", categoryDTO.getDescription());
    }

    @Test
    void testCategoryDTODefaultConstructor() {
        CategoryDTO newCategoryDTO = new CategoryDTO();

        assertNull(newCategoryDTO.getId());
        assertNull(newCategoryDTO.getDescription());
    }

    @Test
    void testCategoryDTOParameterizedConstructor() {
        CategoryDTO newCategoryDTO = new CategoryDTO(1L, "Test Category", "123");

        assertNotNull(newCategoryDTO);
        assertEquals(1L, newCategoryDTO.getId());
        assertEquals("Test Category", newCategoryDTO.getDescription());
    }
}