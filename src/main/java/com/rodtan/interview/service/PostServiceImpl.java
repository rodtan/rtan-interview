package com.rodtan.interview.service;

import com.rodtan.interview.model.Post;
import com.rodtan.interview.model.PostNotFoundException;
import com.rodtan.interview.model.User;
import com.rodtan.interview.model.UserNotFoundException;
import com.rodtan.interview.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;
    UserService userService;

    // unused for now, but was useful for debugging
    Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(int id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("post not found"));
    }

    @Override
    public Post addPost(Post post) {
        User user = null;
        try {
            user = userService.getUser(post.getUserId());
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        try {
            getPostById(post.getId());
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (post.getUser() != null) {
            try {
                userService.getUser(post.getUser().getId());
            } catch (UserNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

        return postRepository.save(post);
    }

    @Override
    public void deletePost(int id) {
        Post oldPost = null;
        try {
            oldPost = getPostById(id);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        postRepository.delete(oldPost);
    }
}
