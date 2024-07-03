package com.rodtan.interview.controller;

import com.rodtan.interview.model.User;
import com.rodtan.interview.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Adds a user",
            description = "Adds a user to the database. The response is User object with id, name, email.",
            tags = { "user" })
    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        logger.warn("user is " + user);
        return userService.addUser(user);
    }

    @Operation(
            summary = "Gets a user",
            description = "Gets a user from the database. The response is User object with id, name, email.",
            tags = { "user" })
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUser(id);
    }

    @Operation(
            summary = "Gets all user",
            description = "Gets all users from the database. The response is a list of User objects with id, name, email.",
            tags = { "user" })
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Updates a user",
            description = "Updates a user in the database. The response is updated User object with id, name, email.",
            tags = { "user" })
    @PutMapping("/users/{id}")
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @Operation(
            summary = "Deletes a user",
            description = "Deletes a user from the database. The response is a 204 status code",
            tags = { "user" })
    @DeleteMapping("/users/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
