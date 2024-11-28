package com.mobisoft.mobisoftapi.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mobisoft.mobisoftapi.repositories.UserGroupRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CategoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserGroupRepository userGroupRepository;

    private UserGroup userGroup;

    @BeforeEach
    public void setUp() {
        userGroup = new UserGroup();
        userGroupRepository.save(userGroup);
    }

    @Test
    public void testCategoryPersistence() {
        Category category = new Category();
        category.setDescription("Categoria de teste");
        category.setUserGroup(userGroup);

        entityManager.persist(category);
        entityManager.flush();
        
        Category savedCategory = entityManager.find(Category.class, category.getId());
        assertNotNull(savedCategory);
        assertEquals("Categoria de teste", savedCategory.getDescription());
        assertEquals(userGroup.getId(), savedCategory.getUserGroup().getId());
    }
}