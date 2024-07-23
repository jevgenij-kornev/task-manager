package com.example.taskmanager.integration;

import com.example.taskmanager.model.Project;
import com.example.taskmanager.repository.ProjectRepository;
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
public class ProjectControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProjectRepository projectRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/projects";
        projectRepository.deleteAll();
    }

    @Test
    void createProjectAndRetrieve() {
        Project project = new Project();
        project.setName("Sample Project");
        project.setDescription("This is a sample project");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Project> request = new HttpEntity<>(project, headers);

        ResponseEntity<Project> responseEntity = restTemplate.postForEntity(baseUrl, request, Project.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(1);
        assertThat(projects.get(0).getName()).isEqualTo("Sample Project");
    }

    @Test
    void getAllProjects() {
        Project project1 = new Project();
        project1.setName("Project 1");
        project1.setDescription("Description for project 1");
        projectRepository.save(project1);

        Project project2 = new Project();
        project2.setName("Project 2");
        project2.setDescription("Description for project 2");
        projectRepository.save(project2);

        ResponseEntity<Project[]> responseEntity = restTemplate.getForEntity(baseUrl, Project[].class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        Project[] projects = responseEntity.getBody();
        assertThat(projects).hasSize(2);
    }

    @Test
    void getProjectById() {
        Project project = new Project();
        project.setName("Sample Project");
        project.setDescription("This is a sample project");
        project = projectRepository.save(project);

        ResponseEntity<Project> responseEntity = restTemplate.getForEntity(baseUrl + "/" + project.getId(), Project.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getName()).isEqualTo("Sample Project");
    }

    @Test
    void updateProject() {
        Project project = new Project();
        project.setName("Sample Project");
        project.setDescription("This is a sample project");
        project = projectRepository.save(project);

        project.setName("Updated Project");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Project> request = new HttpEntity<>(project, headers);

        restTemplate.put(baseUrl + "/" + project.getId(), request);

        Project updatedProject = projectRepository.findById(project.getId()).orElse(null);
        assertThat(updatedProject).isNotNull();
        assertThat(updatedProject.getName()).isEqualTo("Updated Project");
    }

    @Test
    void deleteProject() {
        Project project = new Project();
        project.setName("Sample Project");
        project.setDescription("This is a sample project");
        project = projectRepository.save(project);

        restTemplate.delete(baseUrl + "/" + project.getId());

        assertThat(projectRepository.findById(project.getId())).isEmpty();
    }
}