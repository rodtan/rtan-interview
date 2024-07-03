package com.rodtan.interview.controller;

import com.rodtan.interview.model.Post;
import com.rodtan.interview.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for {@link com.rodtan.interview.model.Post} CRUD operations
 */
@RestController
public class PostController {
    
    @Autowired
    PostService postService;

    // unused for now, but was useful for debugging
    Logger logger = LoggerFactory.getLogger(PostController.class);

    @Operation(
            summary = "Adds a post",
            description = "Adds a post to the database. The response is Post object with id, title, content, user_id",
            tags = { "post" })
    @PostMapping("/posts")
    public Post addPost(@Valid @RequestBody Post post) {
        return postService.addPost(post);
    }

    @Operation(
            summary = "Gets a post",
            description = "Gets a post from the database. The response is Post object with id, title, content, user_id",
            tags = { "post" })
    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }

    @Operation(
            summary = "Gets all posts",
            description = "Gets all posts from the database. The response is a list of Post objects with id, title, content, user_id",
            tags = { "post" })
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(
            summary = "Updates a post",
            description = "Updates a post in the database. The response is updated Post object with id, title, content, user_id",
            tags = { "post" })
    @PutMapping("/posts/{id}")
    public Post updatePost(@Valid @RequestBody Post post) {
        return postService.updatePost(post);
    }

    @Operation(
            summary = "Deletes a post",
            description = "Deletes a post from the database. The response is a 204 status",
            tags = { "post" })
    @DeleteMapping("/posts/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable int id) {
        postService.deletePost(id);
    }
}
