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

        // Mock do User e UserGroup
        User mockUser = mock(User.class);
        when(userService.getLoggedUser()).thenReturn(mockUser);

        UserGroup mockUserGroup = mock(UserGroup.class);
        when(mockUser.getGroup()).thenReturn(mockUserGroup);

        // Inicializando o CategoryDTO para ser usado nos testes
        categoryDTO = new CategoryDTO();
        categoryDTO.setDescription("Category Test");
    }

    @Test
    void testUpdateCategoryNotFound() {
        // Mock de categoria não encontrada
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Executando a atualização e verificando a exceção
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.update(categoryId, categoryDTO);
        });
    }

    @Test
    void testDeleteCategory() {
        // Mock de categoria existente
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Executando a exclusão da categoria
        categoryService.delete(categoryId);

        // Verificando a exclusão
        verify(categoryRepository, times(1)).delete(any(Category.class));
    }

    @Test
    void testDeleteCategoryNotFound() {
        // Mock de categoria não encontrada
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Executando a exclusão e verificando a exceção
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.delete(categoryId);
        });
    }

    @Test
    void testFindAllCategories() {
        // Mock de categorias retornadas do repositório
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryRepository.findByUserGroupId(anyLong())).thenReturn(categories);

        // Executando a busca por todas as categorias
        List<Category> result = categoryService.findAll();

        // Verificando os resultados
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteCategories() {
        // Mock de categorias a serem excluídas
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryRepository.findAllById(ids)).thenReturn(categories);

        // Executando a exclusão de várias categorias
        categoryService.deleteCategories(ids);

        // Verificando a exclusão
        verify(categoryRepository, times(1)).deleteAll(categories);
    }
}