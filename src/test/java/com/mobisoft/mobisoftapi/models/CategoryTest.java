package com.mobisoft.mobisoftapi.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    void testCategoryCreation() {
        UserGroup userGroup = new UserGroup();
        
        Category category = new Category();
        category.setDescription("Test Category");
        category.setUserGroup(userGroup);
        
        assertNotNull(category);
        assertEquals("Test Category", category.getDescription());
        assertEquals(userGroup, category.getUserGroup());
    }
}