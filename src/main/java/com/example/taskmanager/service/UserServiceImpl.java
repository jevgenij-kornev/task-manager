package com.example.taskmanager.service;

import com.example.taskmanager.controller.UserController;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching all users from repository");
        List<User> users = userRepository.findAll();
        logger.info("Found {} users in repository", users.size());
        return users;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("User found: {}", user.get());
        } else {
            logger.warn("User with ID: {} not found", id);
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        logger.info("Saving new user to repository: {}", user);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(Long id, User user) {
        logger.info("Updating user with ID: {} in repository", id);
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            User updatedUser = userRepository.save(existingUser);
            logger.info("User updated: {}", updatedUser);
            return updatedUser;
        }).or(() -> {
            logger.warn("User with ID: {} not found in repository", id);
            return Optional.empty();
        });
    }

    @Override
    public boolean deleteUser(Long id) {
        logger.info("Deleting user with ID: {} from repository", id);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.info("User with ID: {} deleted from repository", id);
            return true;
        } else {
            logger.warn("User with ID: {} not found in repository", id);
            return false;
        }
    }
}