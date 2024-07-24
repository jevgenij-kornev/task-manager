package com.example.taskmanager.controller;

import com.example.taskmanager.model.Project;
import com.example.taskmanager.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        logger.info("Fetching all projects");
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        logger.info("Fetching project with ID: {}", id);
        Optional<Project> project = projectService.getProjectById(id);
        if (project.isPresent()) {
            logger.info("Project found with ID: {}", id);
            return ResponseEntity.ok(project.get());
        } else {
            logger.warn("Project not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        logger.info("Creating new project: {}", project);
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        logger.info("Updating project with ID: {}", id);
        Optional<Project> updatedProject = projectService.updateProject(id, project);
        if (updatedProject.isPresent()) {
            logger.info("Project updated with ID: {}", id);
            return ResponseEntity.ok(updatedProject.get());
        } else {
            logger.warn("Project not found for update with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        logger.info("Deleting project with ID: {}", id);
        if (projectService.deleteProject(id)) {
            logger.info("Project deleted with ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Project not found for deletion with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}