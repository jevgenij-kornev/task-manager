package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Sample Task");

        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));

        List<Task> tasks = taskService.getAllTasks();
        assertEquals(1, tasks.size());
        assertEquals("Sample Task", tasks.get(0).getName());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Sample Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.getTaskById(1L);
        assertTrue(foundTask.isPresent());
        assertEquals("Sample Task", foundTask.get().getName());
    }

    @Test
    void testCreateTask() {
        Task task = new Task();
        task.setName("New Task");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);
        assertEquals("New Task", createdTask.getName());
    }

    @Test
    void testUpdateTask() {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setName("Old Task");

        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setName("Updated Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Optional<Task> result = taskService.updateTask(1L, updatedTask);
        assertTrue(result.isPresent());
        assertEquals("Updated Task", result.get().getName());
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        boolean isDeleted = taskService.deleteTask(1L);
        assertTrue(isDeleted);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}