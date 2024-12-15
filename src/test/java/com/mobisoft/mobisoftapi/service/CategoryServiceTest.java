package com.mobisoft.mobisoftapi.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.mobisoft.mobisoftapi.configs.exceptions.CategoryNotFoundException;
import com.mobisoft.mobisoftapi.dtos.category.CategoryDTO;
import com.mobisoft.mobisoftapi.models.Category;
import com.mobisoft.mobisoftapi.models.User;
import com.mobisoft.mobisoftapi.models.UserGroup;
import com.mobisoft.mobisoftapi.repositories.CategoryRepository;
import com.mobisoft.mobisoftapi.services.CategoryService;
import com.mobisoft.mobisoftapi.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserGroup userGroup;

    @Mock
    private Category category;

    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User mockUser = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(mockUser);

        UserGroup mockUserGroup = mock(UserGroup.class);
        when(mockUser.getGroup()).thenReturn(mockUserGroup);

        categoryDTO = new CategoryDTO();
        categoryDTO.setDescription("Category Test");
    }

    @Test
    void testUpdateCategoryNotFound() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.update(categoryId, categoryDTO);
        });
    }

    @Test
    void testDeleteCategory() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        categoryService.delete(categoryId);

        verify(categoryRepository, times(1)).delete(any(Category.class));
    }

    @Test
    void testDeleteCategoryNotFound() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.delete(categoryId);
        });
    }

    @Test
    void testFindAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryRepository.findByUserGroupId(anyLong())).thenReturn(categories);

        List<Category> result = categoryService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteCategories() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryRepository.findAllById(ids)).thenReturn(categories);

        categoryService.deleteCategories(ids);

        verify(categoryRepository, times(1)).deleteAll(categories);
    }
    
    @Test
    void testCreateCategory() {
        when(userService.getLoggedUser().getGroup()).thenReturn(userGroup);

        categoryDTO.setCode("CAT123");
        categoryDTO.setDescription("New Category");

        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Category result = categoryService.create(categoryDTO);

        assertNotNull(result);
        assertEquals("CAT123", result.getCode());
        assertEquals("New Category", result.getDescription());
        assertEquals(userGroup, result.getUserGroup());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testFindByIdCategoryExists() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Category result = categoryService.findById(categoryId);

        assertNotNull(result);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testFindByIdCategoryNotFound() {
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            categoryService.findById(categoryId);
        });
    }

    @Test
    void testFindByCode() {
        String code = "CAT123";
        when(categoryRepository.findByCode(code)).thenReturn(category);

        Category result = categoryService.findByCode(code);

        assertNotNull(result);
        assertEquals(category, result);
        verify(categoryRepository, times(1)).findByCode(code);
    }
}