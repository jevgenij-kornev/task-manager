package com.example.taskmanager.controller;

import com.example.taskmanager.config.TestSecurityConfig;
import com.example.taskmanager.model.Project;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
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

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import(TestSecurityConfig.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task;

    @BeforeEach
    void setUp() {
        Project project = new Project();
        project.setId(1L);
        project.setName("Sample Project");
        project.setDescription("This is a sample project");

        task = new Task();
        task.setId(1L);
        task.setName("Sample Task");
        task.setDescription("This is a sample task");
        task.setCompleted(false);
        task.setProject(project);
    }

    @Test
    void getAllTasks() throws Exception {
        Mockito.when(taskService.getAllTasks()).thenReturn(Collections.singletonList(task));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(task.getName()));
    }

    @Test
    void getTaskById() throws Exception {
        Mockito.when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(task.getName()));
    }

    @Test
    void createTask() throws Exception {
        Mockito.when(taskService.createTask(Mockito.any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(task.getName()));
    }

    @Test
    void updateTask() throws Exception {
        Mockito.when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));
        Mockito.when(taskService.updateTask(Mockito.eq(1L), Mockito.any(Task.class))).thenReturn(Optional.of(task));

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(task.getName()));
    }

    @Test
    void deleteTask() throws Exception {
        Mockito.when(taskService.deleteTask(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }
}