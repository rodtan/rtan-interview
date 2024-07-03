package com.rodtan.interview.controller;

import com.rodtan.interview.model.Post;
import com.rodtan.interview.service.PostService;
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

    @PostMapping("/posts")
    public Post addPost(@Valid @RequestBody Post Post) {
        return postService.addPost(Post);
    }

    @GetMapping("/posts/{id}")
    public Post getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@Valid @RequestBody Post Post) {
        return postService.updatePost(Post);
    }

    @DeleteMapping("/posts/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable int id) {
        postService.deletePost(id);
    }
}
