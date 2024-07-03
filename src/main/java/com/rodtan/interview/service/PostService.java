package com.rodtan.interview.service;

import com.rodtan.interview.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(int id);
    Post addPost(Post post);
    Post updatePost(Post post);
    void deletePost(int id);
}
