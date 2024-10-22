package com.mobisoft.mobisoftapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobisoft.mobisoftapi.configs.exceptions.ProjectNotFoundException;
import com.mobisoft.mobisoftapi.dtos.project.ProjectDTO;
import com.mobisoft.mobisoftapi.models.Project;
import com.mobisoft.mobisoftapi.repositories.CostumerRepository;
import com.mobisoft.mobisoftapi.repositories.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private CostumerRepository costumerRepository;


    public Project createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setDescription(projectDTO.getDescription());
        project.setNotes(projectDTO.getNotes());
        project.setReferenceDate(projectDTO.getReferenceDate());
        project.setConclusionPending(projectDTO.isConclusionPending());

        return projectRepository.save(project);
    }

    public Project getProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project updateProject(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        project.setDescription(projectDTO.getDescription());
        project.setNotes(projectDTO.getNotes());
        project.setReferenceDate(projectDTO.getReferenceDate());
        project.setConclusionPending(projectDTO.isConclusionPending());

        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException(id);
        }
        projectRepository.deleteById(id);
    }
}
