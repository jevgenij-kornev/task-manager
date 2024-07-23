package com.example.taskmanager.integration;

import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        user = userRepository.save(user);
    }

    @Test
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value(user.getUsername()));
    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    void createUser() throws Exception {
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("newpassword");
        newUser.setEmail("newuser@example.com");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(newUser.getUsername()));

        Optional<User> createdUser = userRepository.findByUsername("newuser");
        assertThat(createdUser).isPresent();
    }

    @Test
    void updateUser() throws Exception {
        user.setUsername("updateduser");

        mockMvc.perform(put("/api/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()));

        Optional<User> updatedUser = userRepository.findById(user.getId());
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getUsername()).isEqualTo("updateduser");
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/" + user.getId()))
                .andExpect(status().isNoContent());

        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertThat(deletedUser).isNotPresent();
    }
}