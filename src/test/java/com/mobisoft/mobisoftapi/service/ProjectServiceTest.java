package com.mobisoft.mobisoftapi.service;

import com.mobisoft.mobisoftapi.dtos.project.ProjectDTO;
import com.mobisoft.mobisoftapi.enums.project.StatusType;
import com.mobisoft.mobisoftapi.models.*;
import com.mobisoft.mobisoftapi.repositories.ProjectRepository;
import com.mobisoft.mobisoftapi.services.CostumerService;
import com.mobisoft.mobisoftapi.services.EmployeesService;
import com.mobisoft.mobisoftapi.services.ProjectService;
import com.mobisoft.mobisoftapi.services.UserService;
import com.mobisoft.mobisoftapi.configs.exceptions.ProjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private CostumerService costumerService;

    @Mock
    private EmployeesService employeesService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private ProjectDTO projectDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        project = new Project();
        project.setId(1L);
        project.setDescription("Project Description");
        project.setNotes("Project Notes");
        project.setReferenceDate(null);
        project.setConclusionPending(true);
        project.setFinancialStatus(StatusType.DELIVERED);
        project.setDeliveryStatus(StatusType.DELIVERED);

        Costumer costumer = new Costumer();
        costumer.setId(1L);
        project.setCostumer(costumer);

        Employees projectDesigner = new Employees();
        projectDesigner.setId(1L);
        project.setProjectDesigner(projectDesigner);

        Employees seller = new Employees();
        seller.setId(1L);
        project.setSeller(seller);

        UserGroup userGroup = new UserGroup();
        userGroup.setId(1L);
        project.setUserGroup(userGroup);
        
        projectDTO = new ProjectDTO();
        projectDTO.setDescription("New Project Description");
        projectDTO.setNotes("New Project Notes");
        projectDTO.setReferenceDate(null);
        projectDTO.setConclusionPending(false);
        projectDTO.setFinancialStatus(StatusType.DELIVERED);
        projectDTO.setDeliveryStatus(StatusType.DELIVERED);
        projectDTO.setCostumerId(1L);
        projectDTO.setProjectDesignerId(1L);
        projectDTO.setSellerId(1L);
    }

    @Test
    public void createProject_ShouldReturnCreatedProject() {
        when(costumerService.getCostumerById(anyLong())).thenReturn(new Costumer());
        when(employeesService.findById(anyLong())).thenReturn(new Employees());
        when(userService.getLoggedUser()).thenReturn(new User());
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project createdProject = projectService.createProject(projectDTO);
        assertNotNull(createdProject);
        assertEquals("New Project Description", createdProject.getDescription());
        assertEquals("New Project Notes", createdProject.getNotes());
        assertEquals(LocalDate.of(2025, 12, 31), createdProject.getReferenceDate());
        assertFalse(createdProject.isConclusionPending());
        assertEquals("Unpaid", createdProject.getFinancialStatus());
        assertEquals("Not Delivered", createdProject.getDeliveryStatus());
        
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void getProjectById_ShouldReturnProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Project foundProject = projectService.getProjectById(1L);
        assertNotNull(foundProject);
        assertEquals(1L, foundProject.getId());
        assertEquals("Project Description", foundProject.getDescription());
    }

    @Test
    public void getProjectById_ShouldThrowProjectNotFoundException_WhenProjectNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> projectService.getProjectById(1L));
    }

    @Test
    public void getAllProjects_ShouldReturnListOfProjects() {
        when(userService.getLoggedUser()).thenReturn(new User());
        when(projectRepository.findByUserGroupId(anyLong())).thenReturn(Arrays.asList(project));
        List<Project> projects = projectService.getAllProjects();
        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Project Description", projects.get(0).getDescription());
    }

    @Test
    public void updateProject_ShouldReturnUpdatedProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(costumerService.getCostumerById(anyLong())).thenReturn(new Costumer());
        when(employeesService.findById(anyLong())).thenReturn(new Employees());
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        Project updatedProject = projectService.updateProject(1L, projectDTO);
        assertNotNull(updatedProject);
        assertEquals("New Project Description", updatedProject.getDescription());
        assertEquals("New Project Notes", updatedProject.getNotes());
        assertEquals(LocalDate.of(2025, 12, 31), updatedProject.getReferenceDate());
        assertFalse(updatedProject.isConclusionPending());
    }

    @Test
    public void deleteProject_ShouldDeleteProject() {
        when(projectRepository.existsById(1L)).thenReturn(true);
        projectService.deleteProject(1L);
        verify(projectRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteProject_ShouldThrowProjectNotFoundException_WhenProjectNotFound() {
        when(projectRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProjectNotFoundException.class, () -> projectService.deleteProject(1L));
    }

    @Test
    public void deleteProjects_ShouldDeleteMultipleProjects() {
        List<Long> ids = Arrays.asList(1L, 2L);
        when(projectRepository.findAllById(ids)).thenReturn(Arrays.asList(project));
        projectService.deleteProjects(ids);

        verify(projectRepository, times(1)).deleteAll(anyList());
    }
}