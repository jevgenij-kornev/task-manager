package com.example.taskmanager.controller;

import com.example.taskmanager.config.TestSecurityConfig;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.repository.ProjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProjectController.class)
@Import(TestSecurityConfig.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(1L);
        project.setName("Sample Project");
        project.setDescription("This is a sample project");
    }

    @Test
    void getAllProjects() throws Exception {
        Mockito.when(projectRepository.findAll()).thenReturn(Arrays.asList(project));

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(project.getName()));
    }

    @Test
    void getProjectById() throws Exception {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(project.getName()));
    }

    @Test
    void createProject() throws Exception {
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(project.getName()));
    }

    @Test
    void updateProject() throws Exception {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);

        mockMvc.perform(put("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(project.getName()));
    }

    @Test
    void deleteProject() throws Exception {
        Mockito.when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        Mockito.doNothing().when(projectRepository).deleteById(1L);

        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isOk());
    }
}