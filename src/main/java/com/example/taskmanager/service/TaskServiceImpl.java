package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        logger.info("Fetching all tasks");
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        logger.info("Fetching task with ID: {}", id);
        return taskRepository.findById(id);
    }

    @Override
    public Task createTask(Task task) {
        logger.info("Creating new task: {}", task);
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> updateTask(Long id, Task task) {
        logger.info("Updating task with ID: {}", id);
        return taskRepository.findById(id)
                .map(existingTask -> {
                    task.setId(id);
                    Task updatedTask = taskRepository.save(task);
                    logger.info("Task updated: {}", updatedTask);
                    return Optional.of(updatedTask);
                })
                .orElseGet(() -> {
                    logger.warn("Task with ID: {} not found", id);
                    return Optional.empty();
                });
    }

    @Override
    public boolean deleteTask(Long id) {
        logger.info("Deleting task with ID: {}", id);
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            logger.info("Task deleted with ID: {}", id);
            return true;
        } else {
            logger.warn("Task not found for deletion with ID: {}", id);
            return false;
        }
    }
}