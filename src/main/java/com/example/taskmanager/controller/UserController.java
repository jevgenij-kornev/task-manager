package com.example.taskmanager.controller;

import com.example.taskmanager.model.User;
import com.example.taskmanager.service.UserService;
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
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        logger.info("Found {} users", users.size());
        return users;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            logger.info("User found: {}", user.get());
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("User with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        logger.info("Creating new user: {}", user);
        User createdUser = userService.createUser(user);
        logger.info("Created user: {}", createdUser);
        return createdUser;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        logger.info("Updating user with ID: {} with data: {}", id, user);
        Optional<User> updatedUser = userService.updateUser(id, user);
        if (updatedUser.isPresent()) {
            logger.info("User updated: {}", updatedUser.get());
            return ResponseEntity.ok(updatedUser.get());
        } else {
            logger.warn("User with ID: {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        if (userService.deleteUser(id)) {
            logger.info("User with ID: {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("User with ID: {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }
    }
}