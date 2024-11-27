package com.mobisoft.mobisoftapi.controllers;

import com.mobisoft.mobisoftapi.dtos.project.ProjectDTO;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    private Project project;
    private ProjectDTO projectDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        project = new Project();
        project.setId(1L);
        project.setDescription("Test Project");
        project.setReferenceDate(Calendar.getInstance());
        projectDTO = new ProjectDTO();
        projectDTO.setDescription("Test Project DTO");
    }

    @Test
    void testCreateProject() {
        when(projectService.createProject(projectDTO)).thenReturn(project);

        ResponseEntity<Project> response = projectController.createProject(projectDTO);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertEquals(project, response.getBody());
        verify(projectService, times(1)).createProject(projectDTO);
    }

    @Test
    void testGetProjectById() {
        when(projectService.getProjectById(1L)).thenReturn(project);

        ResponseEntity<Project> response = projectController.getProjectById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(project, response.getBody());
        verify(projectService, times(1)).getProjectById(1L);
    }

    @Test
    void testGetAllProjects() {
        List<Project> projects = Arrays.asList(project);
        when(projectService.getAllProjects()).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getAllProjects();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(projects, response.getBody());
        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    void testUpdateProject() {
        when(projectService.updateProject(1L, projectDTO)).thenReturn(project);

        ResponseEntity<Project> response = projectController.updateProject(1L, projectDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(project, response.getBody());
        verify(projectService, times(1)).updateProject(1L, projectDTO);
    }

    @Test
    void testDeleteProjects_Success() {
        doNothing().when(projectService).deleteProjects(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = projectController.deleteProjects(Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Categoria(s) deletada(s) com sucesso.", response.getBody());
        verify(projectService, times(1)).deleteProjects(Arrays.asList(1L, 2L));
    }

    @Test
    void testDeleteProjects_DataIntegrityViolation() {
        doThrow(new DataIntegrityViolationException("")).when(projectService).deleteProjects(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = projectController.deleteProjects(Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Não é possível excluir esta categoria, pois ela está em uso.", response.getBody());
        verify(projectService, times(1)).deleteProjects(Arrays.asList(1L, 2L));
    }

    @Test
    void testDeleteProjects_GeneralException() {
        doThrow(new RuntimeException("General error")).when(projectService).deleteProjects(Arrays.asList(1L, 2L));

        ResponseEntity<String> response = projectController.deleteProjects(Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Erro ao processar a solicitação.", response.getBody());
        verify(projectService, times(1)).deleteProjects(Arrays.asList(1L, 2L));
    }
}