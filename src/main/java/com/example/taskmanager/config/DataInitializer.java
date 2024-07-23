package com.example.taskmanager.config;

import com.example.taskmanager.model.Project;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.ProjectRepository;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            if (userRepository.count() == 0) {
                User user = new User();
                user.setUsername("john_doe");
                user.setPassword("password123");
                user.setEmail("john.doe@example.com");
                userRepository.save(user);
            }

            if (projectRepository.count() == 0) {
                Project project = new Project();
                project.setName("Sample Project");
                project.setDescription("This is a sample project");
                projectRepository.save(project);

                Task task1 = new Task();
                task1.setName("Task 1");
                task1.setDescription("Description for task 1");
                task1.setCompleted(false);
                task1.setProject(project);

                Task task2 = new Task();
                task2.setName("Task 2");
                task2.setDescription("Description for task 2");
                task2.setCompleted(false);
                task2.setProject(project);

                taskRepository.save(task1);
                taskRepository.save(task2);
            }
        };
    }
}