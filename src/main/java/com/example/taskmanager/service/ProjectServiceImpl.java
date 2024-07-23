package com.example.taskmanager.service;

import com.example.taskmanager.model.Project;
import com.example.taskmanager.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        logger.info("Fetching all projects");
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        logger.info("Fetching project with ID: {}", id);
        return projectRepository.findById(id);
    }

    @Override
    public Project createProject(Project project) {
        logger.info("Creating new project: {}", project);
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> updateProject(Long id, Project project) {
        logger.info("Updating project with ID: {}", id);
        return projectRepository.findById(id).map(existingProject -> {
            existingProject.setName(project.getName());
            existingProject.setDescription(project.getDescription());
            Project updatedProject = projectRepository.save(existingProject);
            logger.info("Project updated: {}", updatedProject);
            return updatedProject;
        });
    }

    @Override
    public boolean deleteProject(Long id) {
        logger.info("Deleting project with ID: {}", id);
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            logger.info("Project deleted with ID: {}", id);
            return true;
        } else {
            logger.warn("Project not found for deletion with ID: {}", id);
            return false;
        }
    }
}