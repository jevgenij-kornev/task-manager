package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
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
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks() {
        logger.info("Fetching all tasks");
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        logger.info("Fetching task with ID: {}", id);
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isPresent()) {
            logger.info("Task found with ID: {}", id);
            return ResponseEntity.ok(task.get());
        } else {
            logger.warn("Task not found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        logger.info("Creating new task: {}", task);
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        logger.info("Updating task with ID: {}", id);
        Optional<Task> updatedTask = taskService.updateTask(id, task);
        if (updatedTask.isPresent()) {
            logger.info("Task updated with ID: {}", id);
            return ResponseEntity.ok(updatedTask.get());
        } else {
            logger.warn("Task not found for update with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        logger.info("Deleting task with ID: {}", id);
        if (taskService.deleteTask(id)) {
            logger.info("Task deleted with ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Task not found for deletion with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}