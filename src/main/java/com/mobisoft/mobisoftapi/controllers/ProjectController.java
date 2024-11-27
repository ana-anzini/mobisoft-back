package com.mobisoft.mobisoftapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobisoft.mobisoftapi.dtos.project.ProjectDTO;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.services.ProjectService;
import com.mobisoft.mobisoftapi.services.UserService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectDTO projectDTO) {
        Project newProject = projectService.createProject(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        Project updatedProject = projectService.updateProject(id, projectDTO);
        return ResponseEntity.ok(updatedProject);
    }
    
    @DeleteMapping
    public ResponseEntity<String> deleteProjects(@RequestParam List<Long> ids) {
        try {
        	projectService.deleteProjects(ids);
            return ResponseEntity.ok("Categoria(s) deletada(s) com sucesso.");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.ok("Não é possível excluir esta categoria, pois ela está em uso.");
        } catch (Exception e) {
            return ResponseEntity.ok("Erro ao processar a solicitação.");
        }
    }
}
