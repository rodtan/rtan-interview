package com.rodtan.interview.controller;

import com.rodtan.interview.model.User;
import com.rodtan.interview.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for {@link com.rodtan.interview.model.User} CRUD operations
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    // unused for now, but was useful for debugging
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        logger.warn("user is " + user);
        return userService.addUser(user);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUser(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/users/{id}")
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
