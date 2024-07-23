package com.example.taskmanager.integration;

import com.example.taskmanager.model.Project;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.ProjectRepository;
import com.example.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private String baseUrl;
    private Project project;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/tasks";
        taskRepository.deleteAll();
        projectRepository.deleteAll();

        project = new Project();
        project.setName("Sample Project");
        project.setDescription("This is a sample project");
        project = projectRepository.save(project);
    }

    @Test
    void createTaskAndRetrieve() {
        Task task = new Task();
        task.setName("Sample Task");
        task.setDescription("This is a sample task");
        task.setCompleted(false);
        task.setProject(project);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Task> request = new HttpEntity<>(task, headers);

        ResponseEntity<Task> responseEntity = restTemplate.postForEntity(baseUrl, request, Task.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        List<Task> tasks = taskRepository.findAll();
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getName()).isEqualTo("Sample Task");
    }

    @Test
    void getAllTasks() {
        Task task1 = new Task();
        task1.setName("Task 1");
        task1.setDescription("Description for task 1");
        task1.setCompleted(false);
        task1.setProject(project);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setName("Task 2");
        task2.setDescription("Description for task 2");
        task2.setCompleted(false);
        task2.setProject(project);
        taskRepository.save(task2);

        ResponseEntity<Task[]> responseEntity = restTemplate.getForEntity(baseUrl, Task[].class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        Task[] tasks = responseEntity.getBody();
        assertThat(tasks).hasSize(2);
    }

    @Test
    void getTaskById() {
        Task task = new Task();
        task.setName("Sample Task");
        task.setDescription("This is a sample task");
        task.setCompleted(false);
        task.setProject(project);
        task = taskRepository.save(task);

        ResponseEntity<Task> responseEntity = restTemplate.getForEntity(baseUrl + "/" + task.getId(), Task.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getName()).isEqualTo("Sample Task");
    }

    @Test
    void updateTask() {
        Task task = new Task();
        task.setName("Sample Task");
        task.setDescription("This is a sample task");
        task.setCompleted(false);
        task.setProject(project);
        task = taskRepository.save(task);

        task.setName("Updated Task");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Task> request = new HttpEntity<>(task, headers);

        restTemplate.put(baseUrl + "/" + task.getId(), request);

        Task updatedTask = taskRepository.findById(task.getId()).orElse(null);
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getName()).isEqualTo("Updated Task");
    }

    @Test
    void deleteTask() {
        Task task = new Task();
        task.setName("Sample Task");
        task.setDescription("This is a sample task");
        task.setCompleted(false);
        task.setProject(project);
        task = taskRepository.save(task);

        restTemplate.delete(baseUrl + "/" + task.getId());

        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }
}