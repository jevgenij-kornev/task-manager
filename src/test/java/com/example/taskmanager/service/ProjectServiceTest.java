package com.example.taskmanager.service;

import com.example.taskmanager.model.Project;
import com.example.taskmanager.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProjects() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Sample Project");

        when(projectRepository.findAll()).thenReturn(Collections.singletonList(project));

        List<Project> projects = projectService.getAllProjects();
        assertEquals(1, projects.size());
        assertEquals("Sample Project", projects.get(0).getName());
    }

    @Test
    void testGetProjectById() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Sample Project");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Optional<Project> foundProject = projectService.getProjectById(1L);
        assertTrue(foundProject.isPresent());
        assertEquals("Sample Project", foundProject.get().getName());
    }

    @Test
    void testCreateProject() {
        Project project = new Project();
        project.setName("New Project");

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project createdProject = projectService.createProject(project);
        assertEquals("New Project", createdProject.getName());
    }

    @Test
    void testUpdateProject() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Updated Project");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Optional<Project> updatedProject = projectService.updateProject(1L, project);
        assertTrue(updatedProject.isPresent());
        assertEquals("Updated Project", updatedProject.get().getName());
    }

    @Test
    void testDeleteProject() {
        when(projectRepository.existsById(1L)).thenReturn(true);
        doNothing().when(projectRepository).deleteById(1L);

        boolean isDeleted = projectService.deleteProject(1L);
        assertTrue(isDeleted);

        verify(projectRepository, times(1)).deleteById(1L);
    }
}