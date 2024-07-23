package com.example.taskmanager.service;

import com.example.taskmanager.model.Project;
import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> getAllProjects();
    Optional<Project> getProjectById(Long id);
    Project createProject(Project project);
    Optional<Project> updateProject(Long id, Project project);
    boolean deleteProject(Long id);
}